package com.connectorlib;

import com.connectorlib.messages.outbound.PlayerJoin;
import com.connectorlib.messages.outbound.PlayerQuit;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientPlayerEvent;
import net.minecraft.client.MinecraftClient;

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

//		AtomicInteger tickCounter = new AtomicInteger();
//		ClientTickEvent.CLIENT_POST.register(minecraftClient -> {
//			if (tickCounter.incrementAndGet() > analyticsPeriod) {
//				if (minecraftClient.player != null) {
//					ClientPlayerEntity player = minecraftClient.player;
//					Vec3d pos = player.getPos();
//
//					ModConnector.getInstance().send(new PositionData(getIp(),
//						player.getWorld().getDimensionKey().getValue().toString(),
//						(int) pos.x,
//						(int) pos.y,
//						(int) pos.z));
//
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
//					ModConnector.getInstance().send(new PlayerMovement(player));
//
//					ModConnector.getInstance().send(new PlayerEffects(player.getStatusEffects().stream().toList()));
//				}
//
//				tickCounter.set(0);
//			}
//		});
//
//		ClientChatEvent.RECEIVED.register((parameters, message) -> {
//			String senderName = parameters.name().getString();
//			AtomicReference<UUID> senderUuid = new AtomicReference<>(new UUID(0, 0));
//
//			MinecraftClient client = MinecraftClient.getInstance();
//			assert client.player != null;
//			assert client.world != null;
//
//			client.world.getPlayers().forEach(player -> {
//				if (player.getName().getString().equals(senderName)) {
//					senderUuid.set(player.getUuid());
//				}
//			});
//
//			ModConnector.getInstance().send(new ChatData(
//				getIp(),
//				message.getString(),
//				senderName,
//				senderUuid.get().toString(),
//				client.player.getName().getString(),
//				client.player.getUuid().toString()
//			));
//			return CompoundEventResult.pass();
//		});
//
//		ClientSystemMessageEvent.RECEIVED.register(message -> {
//			MinecraftClient client = MinecraftClient.getInstance();
//			assert client.player != null;
//
//			ModConnector.getInstance().send(new SystemChatData(
//				getIp(),
//				message.getString(),
//				client.player.getName().getString(),
//				client.player.getUuid().toString()
//			));
//			return CompoundEventResult.pass();
//		});
//
//		ClientPlayerEvent.CLIENT_PLAYER_RESPAWN.register((oldPlayer, newPlayer) -> {
//			ModConnector.getInstance().send(new PlayerRespawn(getIp(), newPlayer.getUuidAsString()));
//		});
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