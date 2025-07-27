package com.connectorlib;

import com.connectorlib.java_websocket.client.WebSocketClient;
import com.connectorlib.java_websocket.drafts.Draft;
import com.connectorlib.java_websocket.handshake.ServerHandshake;
import com.connectorlib.messages.outbound.IdentityChallenge;
import com.connectorlib.messages.outbound.IdentityRequest;
import com.connectorlib.messages.outbound.NetworkData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.platform.Platform;
import net.minecraft.client.MinecraftClient;
import org.msgpack.jackson.dataformat.MessagePackMapper;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ModConnector {
	private static final Queue<BaseOutboundMessage> outboundQueue = new ConcurrentLinkedQueue<>();
	private static ConnectorClient client;
	private static ModConnector instance;

	private ModConnector() {
		ClientTickEvent.CLIENT_POST.register(minecraftClient -> tick());
		ClientLifecycleEvent.CLIENT_STOPPING.register(minecraftClient -> {
			if (client != null && client.isOpen()) client.close();
		});
	}

	private void tick() {
		if (client != null && client.isOpen()) {
			try {
				BaseOutboundMessage outboundMessage = outboundQueue.poll();
				if (outboundMessage != null) {
					if (!List.of(IdentityChallenge.class, IdentityRequest.class).contains(outboundMessage.getClass())) {
						if (ConnectorClient.token == null) {
							outboundQueue.add(outboundMessage); // Back of the line bucko
						} else {
							byte[] iv = new byte[16];
							new SecureRandom().nextBytes(iv);
							IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
							SecretKeySpec secretKeySpec = new SecretKeySpec(ConnectorClient.token.getBytes(), "AES");

							try {
								Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
								cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
								byte[] encrypted = cipher.doFinal(outboundMessage.ser());

								byte[] msg = new byte[encrypted.length + 16];
								System.arraycopy(iv, 0, msg, 0, 16);
								System.arraycopy(encrypted, 0, msg, 16, encrypted.length);
								client.send(msg);
							} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
											 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
								if (Platform.isDevelopmentEnvironment()) e.printStackTrace();
							}
						}
					} else {
						client.send(outboundMessage.ser());
					}
				}
			} catch (Exception e) {
				if (Platform.isDevelopmentEnvironment()) e.printStackTrace();
			}
		}
	}

	public static void setup() {
		try {
			String analyticsServer = ModConfig.getInstance().get("analyticsServer").getAsString();
			instance = new ModConnector();
			outboundQueue.clear();

			if (client != null && client.isOpen()) client.close();
			client = new ConnectorClient(new URI(analyticsServer));
			client.connectBlocking();
		} catch (InterruptedException | URISyntaxException e) {
			if (Platform.isDevelopmentEnvironment()) e.printStackTrace();
		}
	}

	public static ModConnector getInstance() {
		if (instance == null) {
			instance = new ModConnector();
		}

		return instance;
	}

	public void send(BaseOutboundMessage message) {
		outboundQueue.offer(message);
	}

	public void setToken(String token) {
		ConnectorClient.token = token;
	}

	private static class ConnectorClient extends WebSocketClient {
		public static String token;

		public ConnectorClient(URI serverUri, Draft draft) {
			super(serverUri, draft);
		}

		public ConnectorClient(URI serverURI) {
			super(serverURI);
		}

		@Override
		public void onError(Exception ex) {
			if (Platform.isDevelopmentEnvironment()) ex.printStackTrace();
		}

		@Override
		public void onClose(int code, String reason, boolean remote) {
		}

		@Override
		public void onOpen(ServerHandshake handshakedata) {
			token = null;
			ModConnector.getInstance().send(new IdentityRequest(MinecraftClient.getInstance()
				.getSession()
				.getUuid()
				.replaceAll("-", "")));
			ModConnector.getInstance().send(new NetworkData());
		}

		@Override
		public void onMessage(ByteBuffer bytes) {
			try {
				ObjectMapper objectMapper = new MessagePackMapper();
				List<Object> msg = objectMapper.readValue(bytes.array(), new TypeReference<>() {
				});

				String type = (String) msg.get(0);
				Object payload = msg.get(1);

				((BaseInboundMessage) objectMapper.convertValue(payload,
					Class.forName("com.connectorlib.messages.inbound." + type))).respond();
			} catch (IOException | ClassNotFoundException e) {
				if (Platform.isDevelopmentEnvironment()) e.printStackTrace();
			}
		}

		@Override
		public void onMessage(String message) {
			// we only deal in ByteBuffers round here
		}
	}
}
