package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;

public class PlayerQuit extends BaseMessage {
	String ip;
	String uuid;

	public PlayerQuit(String ip, String uuid) {
		this.ip = ip;
		this.uuid = uuid;
	}
}