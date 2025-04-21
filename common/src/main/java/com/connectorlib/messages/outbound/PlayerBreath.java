package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;

public class PlayerBreath extends BaseMessage {
	Integer breath;

	public PlayerBreath(Integer breath) {
		this.breath = breath;
	}
}