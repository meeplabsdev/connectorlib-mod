package com.connectorlib.messages;

import com.connectorlib.BaseMessage;

public class PlayerAdvancement extends BaseMessage {
	String ip;
	String uuid;
	String advancement;

	public PlayerAdvancement(String ip, String uuid, String advancement) {
		this.ip = ip;
		this.uuid = uuid;
		this.advancement = advancement;
	}
}