package com.connectorlib.messages.outbound.old;

import com.connectorlib.BaseOutboundMessage;

public class PlayerHunger extends BaseOutboundMessage {
	Integer hunger;
	Integer saturation;
	Integer exhaustion;

	public PlayerHunger(Integer hunger, Integer saturation, Integer exhaustion) {
		this.hunger = hunger;
		this.saturation = saturation;
		this.exhaustion = exhaustion;
	}
}