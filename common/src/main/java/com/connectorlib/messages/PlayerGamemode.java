package com.connectorlib.messages;

import com.connectorlib.BaseMessage;
import net.minecraft.client.network.ClientPlayerEntity;

public class PlayerGamemode extends BaseMessage {
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