package com.connectorlib.messages;

import com.connectorlib.BaseMessage;
import net.minecraft.client.network.ClientPlayerEntity;

public class PlayerMovement extends BaseMessage {
	Boolean sneaking;
	Boolean sprinting;
	Boolean swimming;
	Boolean crawling;
	Boolean flying;
	Boolean onGround;
	Integer velX;
	Integer velY;
	Integer velZ;

	public PlayerMovement(ClientPlayerEntity player) {
		this.sneaking = player.isSneaking();
		this.sprinting = player.isSprinting();
		this.swimming = player.isSwimming();
		this.crawling = player.isCrawling();
		this.flying = player.isFallFlying();
		this.onGround = player.isOnGround();
		this.velX = (int) player.getVelocity().x;
		this.velY = (int) player.getVelocity().y;
		this.velZ = (int) player.getVelocity().z;
	}
}