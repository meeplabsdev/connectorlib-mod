package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;

public class PlayerRespawn extends BaseMessage {
	String ip;
	String uuid;

	public PlayerRespawn(String ip, String uuid) {
		this.ip = ip;
		this.uuid = uuid;
	}
}