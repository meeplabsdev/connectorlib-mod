package com.connectorlib;

import com.connectorlib.messages.IdentityRequest;
import com.connectorlib.messages.NetworkData;
import com.connectorlib.messages.PositionData;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.concurrent.atomic.AtomicInteger;

public final class ConnectorLibMod {
	public static final String MOD_ID = "connectorlib";
	public static final String ghUser = "meeplabsdev";
	public static final String repoName = "connectorlib-mod";

	public static void init() {
		if (!ModConfig.getInstance().get("analyticsEnabled").getAsBoolean()) return;
		final int analyticsPeriod = ModConfig.getInstance().get("analyticsPeriod").getAsInt() / 50;

		ClientLifecycleEvent.CLIENT_SETUP.register(minecraftClient -> {
			String username = minecraftClient.getSession().getUsername();
			String uuid = minecraftClient.getSession().getUuid();

			ModConnector.getInstance().send(new IdentityRequest(username, uuid));
			ModConnector.getInstance().send(new NetworkData());
		});

		AtomicInteger tickCounter = new AtomicInteger();
		ClientTickEvent.CLIENT_POST.register(minecraftClient -> {
			tickCounter.getAndIncrement();
			if (tickCounter.get() > analyticsPeriod) {
				if (minecraftClient.player != null) {
					ClientPlayerEntity player = minecraftClient.player;
					Vec3d pos = player.getPos();

					String ip = "unknown";
					if (minecraftClient.getCurrentServerEntry() != null) {
						ip = minecraftClient.getCurrentServerEntry().address;
					}

					ModConnector.getInstance().send(new PositionData(ip, (int) pos.x, (int) pos.y, (int) pos.z));
				}

				tickCounter.set(0);
			}
		});
	}
}
