package com.connectorlib;

import com.connectorlib.messages.*;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.events.client.*;
import net.minecraft.client.MinecraftClient;
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

					ModConnector.getInstance().send(new PositionData(getIp(), (int) pos.x, (int) pos.y, (int) pos.z));
				}

				tickCounter.set(0);
			}
		});

		ClientChatEvent.RECEIVED.register((type, message) -> {
			ModConnector.getInstance().send(new ChatData(getIp(), message.getString()));
			return CompoundEventResult.pass();
		});

		ClientSystemMessageEvent.RECEIVED.register(message -> {
			ModConnector.getInstance().send(new SystemChatData(getIp(), message.getString()));
			return CompoundEventResult.pass();
		});

		ClientPlayerEvent.CLIENT_PLAYER_JOIN.register(player -> {
			ModConnector.getInstance().send(new PlayerJoin(getIp(), player.getName().getString()));
		});

		ClientPlayerEvent.CLIENT_PLAYER_QUIT.register(player -> {
			if (player != null) {
				ModConnector.getInstance().send(new PlayerQuit(getIp(), player.getName().getString()));
			}
		});

		ClientPlayerEvent.CLIENT_PLAYER_RESPAWN.register((oldPlayer, newPlayer) -> {
			ModConnector.getInstance().send(new PlayerRespawn(getIp(), newPlayer.getName().getString()));
		});
	}

	private static String getIp() {
		MinecraftClient minecraftClient = MinecraftClient.getInstance();

		String ip = "unknown";
		if (minecraftClient.getCurrentServerEntry() != null) {
			ip = minecraftClient.getCurrentServerEntry().address;
		}

		return ip;
	}
}
