package com.connectorlib.messages.inbound;

import com.connectorlib.BaseInboundMessage;
import com.connectorlib.ModConnector;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentityChallenge extends BaseInboundMessage {
	@JsonCreator
	public IdentityChallenge(@JsonProperty("token") String token) {
		ModConnector.getInstance().setToken(token);
	}
}
