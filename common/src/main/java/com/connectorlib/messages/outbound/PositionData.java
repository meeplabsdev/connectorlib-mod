package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

import java.util.*;

public class PositionData extends BaseMessage {
	String ip;
	String dimension;
	List<Integer> pos = new ArrayList<>(3);
	HashMap<String, List<Integer>> nearbyPlayers = new HashMap<>();

	public PositionData(String ip, String dimension, Integer x, Integer y, Integer z) {
		this.ip = ip;
		this.dimension = dimension;
		this.pos.add(x);
		this.pos.add(y);
		this.pos.add(z);

		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player == null || client.world == null) return;

		List<PlayerEntity> nearby = client.world.getEntitiesByType(EntityType.PLAYER,
			new Box(x - 100, y - 100, z - 100, x + 100, y + 100, z + 100),
			Objects::nonNull);

		for (PlayerEntity player : nearby) {
			nearbyPlayers.put(player.getName().getString(), List.of(x, y, z));
		}
	}
}