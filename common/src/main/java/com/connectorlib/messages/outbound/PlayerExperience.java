package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;

public class PlayerExperience extends BaseMessage {
	Integer level;
	Float progress;

	public PlayerExperience(Integer level, Float progress) {
		this.level = level;
		this.progress = progress;
	}
}