package com.connectorlib.messages.outbound.old;

import com.connectorlib.BaseOutboundMessage;

public class PlayerBreath extends BaseOutboundMessage {
	Integer breath;

	public PlayerBreath(Integer breath) {
		this.breath = breath;
	}
}