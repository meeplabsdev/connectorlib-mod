package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;

public class PlayerHealth extends BaseMessage {
	Integer health;

	public PlayerHealth(Integer health) {
		this.health = health;
	}
}