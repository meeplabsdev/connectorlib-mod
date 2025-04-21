package com.connectorlib.messages;

import com.connectorlib.BaseMessage;

public class PlayerHunger extends BaseMessage {
	Integer hunger;
	Integer saturation;
	Integer exhaustion;

	public PlayerHunger(Integer hunger, Integer saturation, Integer exhaustion) {
		this.hunger = hunger;
		this.saturation = saturation;
		this.exhaustion = exhaustion;
	}
}