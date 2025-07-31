package com.connectorlib.messages.outbound;

import com.connectorlib.BaseOutboundMessage;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

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
	}

	private ArrayList<Float> nom(Vec3d vec) {
		return new ArrayList<>() {{
			add((float) vec.x);
			add((float) vec.y);
			add((float) vec.z);
		}};
	}
}