package com.connectorlib.fabric.client;

import com.connectorlib.ConnectorLibMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

public final class ConnectorLibModFabricClient implements ClientModInitializer {
	private static final int MESSAGE_INTERVAL_TICKS = 3 * 20;
	private int tickCounter = 0;

	@Override
	public void onInitializeClient() {
		String name = MinecraftClient.getInstance().getSession().getUsername();
		String puuid = MinecraftClient.getInstance().getSession().getUuid();

		try {
			ConnectorLibMod.client.send("1|" + puuid + "|" + name);
		} catch (Exception ignored) {
		}

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			tickCounter++;
			if (tickCounter >= MESSAGE_INTERVAL_TICKS) {
				if (client.player != null && client.getServer() != null) {
					ClientPlayerEntity player = client.player;
					String ip = client.getServer().getServerIp();
					String suuid = player.getUuidAsString();
					Vec3d pos = player.getPos();
					String location = (int) pos.x + "," + (int) pos.y + "," + (int) pos.z;

					try {
						ConnectorLibMod.client.send("2|" + ip + "|" + suuid + "|" + location);
					} catch (Exception ignored) {
					}
				}

				tickCounter = 0;
			}
		});
	}
}
