package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;

public class PlayerPing extends BaseMessage {
	Integer ping;

	public PlayerPing(Integer ping) {
		this.ping = ping;
	}
}