package com.connectorlib;

import com.connectorlib.messages.*;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.events.client.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
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
			ModConnector.setup(username, uuid);
		});

		AtomicInteger tickCounter = new AtomicInteger();
		ClientTickEvent.CLIENT_POST.register(minecraftClient -> {
			tickCounter.getAndIncrement();
			if (tickCounter.get() > analyticsPeriod) {
				if (minecraftClient.player != null) {
					ClientPlayerEntity player = minecraftClient.player;
					Vec3d pos = player.getPos();

					ModConnector.getInstance().send(new PositionData(getIp(),
						player.getWorld().getDimensionKey().getValue().toString(),
						(int) pos.x,
						(int) pos.y,
						(int) pos.z));

					ModConnector.getInstance().send(new ChunkRequest(getIp(),
						player.getWorld(),
						player.getWorld().getChunk(player.getBlockPos())));

					ModConnector.getInstance().send(new PlayerHealth((int) player.getHealth()));

					HungerManager hm = player.getHungerManager();
					ModConnector.getInstance().send(new PlayerHunger(
						hm.getFoodLevel(),
						(int) hm.getSaturationLevel(),
						(int) hm.getExhaustion()));

					ModConnector.getInstance().send(new PlayerBreath(player.getAir()));

					ModConnector.getInstance().send(new PlayerExperience(player.experienceLevel, player.experienceProgress));

					ModConnector.getInstance().send(new PlayerGamemode(player));

					ClientPlayNetworkHandler nh = player.networkHandler;
					if (nh != null && nh.getPlayerList() != null) {
						PlayerListEntry entry = nh.getPlayerListEntry(player.getUuid());
						if (entry != null) {
							ModConnector.getInstance().send(new PlayerPing(entry.getLatency()));
						}
					}

					ModConnector.getInstance().send(new PlayerFPS(minecraftClient.getCurrentFps()));

					PlayerInventory inventory = player.getInventory();
					ModConnector.getInstance().send(new PlayerHotbar(
						inventory.selectedSlot,
						inventory.getStack(0),
						inventory.getStack(1),
						inventory.getStack(2),
						inventory.getStack(3),
						inventory.getStack(4),
						inventory.getStack(5),
						inventory.getStack(6),
						inventory.getStack(7),
						inventory.getStack(8),
						inventory.offHand.get(0)));

					DefaultedList<ItemStack> armor = inventory.armor;
					ModConnector.getInstance().send(new PlayerArmor(
						armor.get(3),
						armor.get(2),
						armor.get(1),
						armor.get(0)));

					ModConnector.getInstance().send(new PlayerMovement(player));
				}

				tickCounter.set(0);
			}
		});

		ClientChatEvent.RECEIVED.register((parameters, message) -> {
			ModConnector.getInstance().send(new ChatData(
				getIp(), message.getString(),
				parameters.name().getString(),
				parameters.targetName() != null ? parameters.targetName().getString() : ""
			));
			return CompoundEventResult.pass();
		});

		ClientSystemMessageEvent.RECEIVED.register(message -> {
			String to = MinecraftClient.getInstance().getSession().getUuid();
			ModConnector.getInstance().send(new SystemChatData(getIp(), message.getString(), to));
			return CompoundEventResult.pass();
		});

		ClientPlayerEvent.CLIENT_PLAYER_JOIN.register(player -> {
			ModConnector.getInstance().send(new PlayerJoin(getIp(), player.getUuidAsString()));
		});

		ClientPlayerEvent.CLIENT_PLAYER_QUIT.register(player -> {
			if (player != null) {
				ModConnector.getInstance().send(new PlayerQuit(getIp(), player.getUuidAsString()));
			}
		});

		ClientPlayerEvent.CLIENT_PLAYER_RESPAWN.register((oldPlayer, newPlayer) -> {
			ModConnector.getInstance().send(new PlayerRespawn(getIp(), newPlayer.getUuidAsString()));
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