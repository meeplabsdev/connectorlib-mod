package com.connectorlib;

import com.connectorlib.messages.outbound.ClientChat;
import com.connectorlib.messages.outbound.ClientPosition;
import com.connectorlib.messages.outbound.PlayerJoin;
import com.connectorlib.messages.outbound.PlayerQuit;
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

		ClientChatEvent.RECEIVED.register((parameters, message) -> {
			ModConnector.getInstance().send(new ClientChat(parameters, message));
			return CompoundEventResult.pass();
		});

//		ClientPlayerEvent.CLIENT_PLAYER_RESPAWN.register((oldPlayer, newPlayer) -> {
//			ModConnector.getInstance().send(new PlayerRespawn(getIp(), newPlayer.getUuidAsString()));
//		});

		AtomicInteger tickCounter = new AtomicInteger();
		ClientTickEvent.CLIENT_POST.register(minecraftClient -> {
			if (tickCounter.incrementAndGet() > analyticsPeriod) {
				tickCounter.set(0);

				if (minecraftClient.player != null) {
					ClientPlayerEntity player = minecraftClient.player;

					ModConnector.getInstance().send(new ClientPosition(player));

//					ModConnector.getInstance().send(new PlayerHealth((int) player.getHealth()));
//
//					HungerManager hm = player.getHungerManager();
//					ModConnector.getInstance().send(new PlayerHunger(
//						hm.getFoodLevel(),
//						(int) hm.getSaturationLevel(),
//						(int) hm.getExhaustion()));
//
//					ModConnector.getInstance().send(new PlayerBreath(player.getAir()));
//
//					ModConnector.getInstance().send(new PlayerExperience(player.experienceLevel, player.experienceProgress));
//
//					ModConnector.getInstance().send(new PlayerGamemode(player));
//
//					ClientPlayNetworkHandler nh = player.networkHandler;
//					if (nh != null && nh.getPlayerList() != null) {
//						PlayerListEntry entry = nh.getPlayerListEntry(player.getUuid());
//						if (entry != null) {
//							ModConnector.getInstance().send(new PlayerPing(entry.getLatency()));
//						}
//					}
//
//					ModConnector.getInstance().send(new PlayerFPS(minecraftClient.getCurrentFps()));
//
//					ItemStack[] itemStacks = new ItemStack[10];
//					PlayerInventory inventory = player.getInventory();
//					for (int i = 0; i < 9; i++) itemStacks[i] = inventory.getStack(i);
//					itemStacks[9] = inventory.offHand.get(0);
//
//					ModConnector.getInstance().send(new PlayerHotbar(
//						inventory.selectedSlot,
//						itemStacks
//						));
//
//					DefaultedList<ItemStack> armor = inventory.armor;
//					ModConnector.getInstance().send(new PlayerArmor(
//						armor.get(3),
//						armor.get(2),
//						armor.get(1),
//						armor.get(0)));
//
//					ModConnector.getInstance().send(new PlayerEffects(player.getStatusEffects().stream().toList()));
					ModConnector.getInstance().send(new ClientAttributes(minecraftClient));
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