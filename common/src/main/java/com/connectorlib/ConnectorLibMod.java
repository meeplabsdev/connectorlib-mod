package com.connectorlib;

import com.connectorlib.java_websocket.client.WebSocketClient;
import com.connectorlib.java_websocket.drafts.Draft;
import com.connectorlib.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public final class ConnectorLibMod {
	public static final String MOD_ID = "connectorlib";
	public static WebSocketClient client;

	public static void init() throws URISyntaxException, InterruptedException {
		try {
			client = new ConnectorClient(new URI("wss://server-general.skunk-snake.ts.net"));
			client.connectBlocking();
		} catch (Exception ignored) {}
	}

	public static class ConnectorClient extends WebSocketClient {

		public ConnectorClient(URI serverUri, Draft draft) {
			super(serverUri, draft);
		}

		public ConnectorClient(URI serverURI) {
			super(serverURI);
		}

		@Override
		public void onOpen(ServerHandshake handshakedata) {
		}

		@Override
		public void onMessage(String message) {
		}

		@Override
		public void onClose(int code, String reason, boolean remote) {
		}

		@Override
		public void onError(Exception ex) {
		}
	}
}
