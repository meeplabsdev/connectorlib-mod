package com.connectorlib;

import com.connectorlib.messages.outbound.*;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.events.client.ClientChatEvent;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

import java.util.concurrent.atomic.AtomicInteger;

public final class ConnectorLibMod {
	public static final String MOD_ID = "connectorlib";
	public static final String ghUser = "meeplabsdev";
	public static final String repoName = "connectorlib-mod";

	public static void init() {
		if (!ModConfig.getInstance().get("analyticsEnabled").getAsBoolean()) return;
		final int analyticsPeriod = ModConfig.getInstance().get("analyticsPeriod").getAsInt() / 50;

		ClientLifecycleEvent.CLIENT_SETUP.register(minecraftClient -> ModConnector.setup());

		ClientPlayerEvent.CLIENT_PLAYER_JOIN.register(player -> ModConnector.getInstance().send(new PlayerJoin(ip())));
		ClientPlayerEvent.CLIENT_PLAYER_QUIT.register(player -> ModConnector.getInstance().send(new PlayerQuit(ip())));
		ClientPlayerEvent.CLIENT_PLAYER_RESPAWN.register((oldPlayer, newPlayer) -> ModConnector.getInstance()
			.send(new PlayerRespawn(oldPlayer.getPos())));

		ClientChatEvent.RECEIVED.register((parameters, message) -> {
			ModConnector.getInstance().send(new ClientChat(parameters, message));
			return CompoundEventResult.pass();
		});

		AtomicInteger tickCounter = new AtomicInteger();
		ClientTickEvent.CLIENT_POST.register(minecraftClient -> {
			if (tickCounter.incrementAndGet() > analyticsPeriod) {
				tickCounter.set(0);

				if (minecraftClient.player != null) {
					ClientPlayerEntity player = minecraftClient.player;

					ModConnector.getInstance().send(new ClientPosition(player));
					ModConnector.getInstance().send(new ClientAttributes(minecraftClient));
					ModConnector.getInstance().send(new ClientInventory(player.getInventory()));
				}
			}
		});
	}

	private static String ip() {
		MinecraftClient minecraftClient = MinecraftClient.getInstance();

		String ip = "unknown";
		if (minecraftClient.getCurrentServerEntry() != null) {
			ip = minecraftClient.getCurrentServerEntry().address;
		}

		return ip;
	}
}