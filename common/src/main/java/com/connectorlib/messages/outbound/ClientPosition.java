package com.connectorlib.messages.outbound;

import com.connectorlib.BaseOutboundMessage;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClientPosition extends BaseOutboundMessage {
	public ArrayList<Float> position;
	public ArrayList<Float> velocity;
	public String dimension;
	public Boolean sneaking;
	public Boolean sprinting;
	public Boolean swimming;
	public Boolean crawling;
	public Boolean grounded;
	public Boolean fallflying;
	public Map<String, ArrayList<Float>> nearby = new HashMap<>();

	public ClientPosition(ClientPlayerEntity player) {
		this.position = nom(player.getPos());
		this.velocity = nom(player.getVelocity());
		this.dimension = player.clientWorld.getDimensionKey().getValue().toTranslationKey();
		this.sneaking = player.isSneaking();
		this.sprinting = player.isSprinting();
		this.swimming = player.isSwimming();
		this.crawling = player.isCrawling();
		this.grounded = player.isOnGround();
		this.fallflying = player.isFallFlying();

		BlockPos pos = player.getBlockPos();
		player.clientWorld.getOtherEntities(player,
				new Box(pos.add(-512, -512, -512), pos.add(512, 512, 512)),
				entity -> entity instanceof PlayerEntity && !Objects.equals(entity.getUuidAsString(), player.getUuidAsString()))
			.forEach(entity -> nearby.put(entity.getUuidAsString(), nom(entity.getPos())));
	}

	private ArrayList<Float> nom(Vec3d vec) {
		return new ArrayList<>() {{
			add((float) vec.x);
			add((float) vec.y);
			add((float) vec.z);
		}};
	}
}