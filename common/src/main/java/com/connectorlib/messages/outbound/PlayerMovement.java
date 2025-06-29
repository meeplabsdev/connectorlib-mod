package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;
import net.minecraft.client.network.ClientPlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class PlayerMovement extends BaseMessage {
	Boolean sneaking;
	Boolean sprinting;
	Boolean swimming;
	Boolean crawling;
	Boolean onGround;
	Boolean fallFlying;
	List<Double> velocity = new ArrayList<>(3);

	public PlayerMovement(ClientPlayerEntity player) {
		this.sneaking = player.isSneaking();
		this.sprinting = player.isSprinting();
		this.swimming = player.isSwimming();
		this.crawling = player.isCrawling();
		this.onGround = player.isOnGround();
		this.fallFlying = player.isFallFlying();
		this.velocity.add(player.getVelocity().x);
		this.velocity.add(player.getVelocity().y);
		this.velocity.add(player.getVelocity().z);
	}
}