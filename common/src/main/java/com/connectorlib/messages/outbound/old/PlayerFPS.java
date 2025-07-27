package com.connectorlib.messages.outbound.old;

import com.connectorlib.BaseOutboundMessage;

public class PlayerFPS extends BaseOutboundMessage {
	Integer fps;

	public PlayerFPS(Integer fps) {
		this.fps = fps;
	}
}