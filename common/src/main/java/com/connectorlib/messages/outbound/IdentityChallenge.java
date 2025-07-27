package com.connectorlib.messages.outbound;

import com.connectorlib.BaseOutboundMessage;


public class IdentityChallenge extends BaseOutboundMessage {
	public String authenticity;
	public String serverId;

	public IdentityChallenge(String authenticity, String serverId) {
		this.authenticity =	authenticity;
		this.serverId = serverId;
	}
}