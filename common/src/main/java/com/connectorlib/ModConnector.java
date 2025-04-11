package com.connectorlib;

import com.connectorlib.java_websocket.client.WebSocketClient;
import com.connectorlib.java_websocket.drafts.Draft;
import com.connectorlib.java_websocket.handshake.ServerHandshake;
import com.connectorlib.messages.IdentityChallenge;
import com.connectorlib.messages.IdentityRequest;
import com.connectorlib.messages.IdentitySession;
import com.connectorlib.messages.NetworkData;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientTickEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.net.URI;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ModConnector {
	private static final Queue<BaseMessage> outboundQueue = new ConcurrentLinkedQueue<>();
	private static final HashMap<String, Class<? extends BaseMessage>> messageMap = new HashMap<>();
	private static ModConnector instance;
	private static WebSocketClient client;
	private static String sessionId;

	private ModConnector() {
		messageMap.put("IdentityChallenge", IdentityChallenge.class);
		messageMap.put("IdentityRequest", IdentityRequest.class);
		messageMap.put("IdentitySession", IdentitySession.class);
		messageMap.put("NetworkData", NetworkData.class);

		String analyticsServer = ModConfig.getInstance().get("analyticsServer").getAsString();

		try {
			client = new ConnectorClient(new URI(analyticsServer));
			client.connectBlocking();
		} catch (Exception ignored) {
		}

		ClientTickEvent.CLIENT_POST.register(minecraftClient -> tick());
		ClientLifecycleEvent.CLIENT_STOPPING.register(minecraftClient -> client.close());
	}

	private void tick() {
		BaseMessage outboundMessage = outboundQueue.peek();
		if (outboundMessage != null) {
			if (sessionId == null && outboundMessage.authRequired) {
				outboundQueue.add(outboundQueue.poll()); // Back of the line bucko
			} else {
				outboundQueue.remove();
				outboundMessage.session = sessionId;
				client.send(outboundMessage.jsonify());
			}
		}
	}

	public static ModConnector getInstance() {
		if (instance == null) {
			instance = new ModConnector();
		}

		return instance;
	}

	public void send(BaseMessage message) {
		outboundQueue.offer(message);
	}

	public void setSession(String session) {
		sessionId = session;
	}

	private static class ConnectorClient extends WebSocketClient {

		public ConnectorClient(URI serverUri, Draft draft) {
			super(serverUri, draft);
		}

		public ConnectorClient(URI serverURI) {
			super(serverURI);
		}

		@Override
		public void onError(Exception ex) {
			ex.printStackTrace();
		}

		@Override
		public void onClose(int code, String reason, boolean remote) {
		}

		@Override
		public void onOpen(ServerHandshake handshakedata) {
		}

		@Override
		public void onMessage(String message) {
			JsonObject json = JsonParser.parseString(message).getAsJsonObject();
			JsonElement _id = json.remove("id");

			if (_id == null) return;
			String id = _id.getAsString();

			if (messageMap.containsKey(id)) {
				Class<?> clazz = messageMap.get(id);
				Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
				Parameter[] parameters = constructor.getParameters();
				Object[] args = new Object[parameters.length];

				for (int i = 0; i < parameters.length; i++) {
					JsonElement jsonElement = json.asMap().values().stream().toList().get(i);
					if (jsonElement != null) {
						args[i] = new GsonBuilder().create().fromJson(jsonElement, parameters[i].getType());
					}
				}

				try {
					BaseMessage instance = (BaseMessage) constructor.newInstance(args);
					outboundQueue.add(instance);
				} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
