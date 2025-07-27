package com.connectorlib.messages.outbound.old;

import com.connectorlib.BaseOutboundMessage;
import net.minecraft.client.network.ClientPlayerEntity;

public class PlayerGamemode extends BaseOutboundMessage {
	String gamemode;

	public PlayerGamemode(ClientPlayerEntity player) {
		if (player.isCreative()) {
			this.gamemode = "creative";
		} else if (player.isSpectator()) {
			this.gamemode = "spectator";
		} else if (player.getAbilities().allowModifyWorld) {
			this.gamemode = "survival";
		} else {
			this.gamemode = "adventure";
		}
	}
}