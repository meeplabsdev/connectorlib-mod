package com.connectorlib;

import com.connectorlib.messages.IdentityRequest;
import com.connectorlib.messages.NetworkData;
import dev.architectury.event.events.client.ClientLifecycleEvent;

public final class ConnectorLibMod {
	public static final String MOD_ID = "connectorlib";
	public static final String ghUser = "meeplabsdev";
	public static final String repoName = "connectorlib-mod";

	public static void init() {
		ClientLifecycleEvent.CLIENT_SETUP.register(minecraftClient -> {
			String username = minecraftClient.getSession().getUsername();
			String uuid = minecraftClient.getSession().getUuid();

			ModConnector.getInstance().send(new IdentityRequest(username, uuid));
			ModConnector.getInstance().send(new NetworkData());
		});
	}
}
