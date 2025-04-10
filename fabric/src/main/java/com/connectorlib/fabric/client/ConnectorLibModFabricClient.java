package com.connectorlib.fabric.client;

import com.connectorlib.ModConfig;
import com.connectorlib.ModConnector;
import com.connectorlib.messages.PositionData;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

public final class ConnectorLibModFabricClient implements ClientModInitializer {
	private int tickCounter = 0;

	@Override
	public void onInitializeClient() {
		if (!ModConfig.getInstance().get("analyticsEnabled").getAsBoolean()) return;
		int analyticsPeriod = ModConfig.getInstance().get("analyticsPeriod").getAsInt() / 50;

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			tickCounter++;
			if (tickCounter >= analyticsPeriod) {
				if (client.player != null) {
					ClientPlayerEntity player = client.player;
					Vec3d pos = player.getPos();

					String ip = "unknown";
					if (client.getCurrentServerEntry() != null) {
						ip = client.getCurrentServerEntry().address;
					}

					ModConnector.getInstance().send(new PositionData(ip, (int) pos.x, (int) pos.y, (int) pos.z));
				}

				tickCounter = 0;
			}
		});
	}
}
