package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;

public class PlayerFPS extends BaseMessage {
	Integer fps;

	public PlayerFPS(Integer fps) {
		this.fps = fps;
	}
}