package com.connectorlib.messages.outbound.old;

import com.connectorlib.BaseOutboundMessage;

public class PlayerHealth extends BaseOutboundMessage {
	Integer health;

	public PlayerHealth(Integer health) {
		this.health = health;
	}
}