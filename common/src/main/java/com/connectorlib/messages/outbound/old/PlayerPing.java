package com.connectorlib.messages.outbound.old;

import com.connectorlib.BaseOutboundMessage;

public class PlayerPing extends BaseOutboundMessage {
	Integer ping;

	public PlayerPing(Integer ping) {
		this.ping = ping;
	}
}