package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PositionData extends BaseMessage {
	String ip;
	String dimension;
	Integer x;
	Integer y;
	Integer z;
	ArrayList<Map<String, Integer>> nearbyPlayers = new ArrayList<>();

	public PositionData(String ip, String dimension, Integer x, Integer y, Integer z) {
		this.ip = ip;
		this.dimension = dimension;
		this.x = x;
		this.y = y;
		this.z = z;

		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player == null || client.world == null) return;

		Vec3d pos = client.player.getPos();
		List<PlayerEntity> nearby = client.world.getEntitiesByType(EntityType.PLAYER,
			new Box(pos.x - 100, pos.y - 100, pos.z - 100, pos.x + 100, pos.y + 100, pos.z + 100),
			Objects::nonNull);

		for (PlayerEntity player : nearby) {
			nearbyPlayers.add(Map.of(player.getName().getString(), (int) client.player.distanceTo(player)));
		}
	}
}