package com.connectorlib.messages;

import com.connectorlib.BaseMessage;

public class PlayerJoin extends BaseMessage {
	String ip;
	String uuid;

	public PlayerJoin(String ip, String uuid) {
		this.ip = ip;
		this.uuid = uuid;
	}
}