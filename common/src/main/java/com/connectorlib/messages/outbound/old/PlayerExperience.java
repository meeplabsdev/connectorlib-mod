package com.connectorlib.messages.outbound.old;

import com.connectorlib.BaseOutboundMessage;

public class PlayerExperience extends BaseOutboundMessage {
	Integer level;
	Float progress;

	public PlayerExperience(Integer level, Float progress) {
		this.level = level;
		this.progress = progress;
	}
}