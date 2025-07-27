package com.connectorlib.messages.outbound;

import com.connectorlib.BaseOutboundMessage;

public class IdentityRequest extends BaseOutboundMessage {
	public String uuid;

	public IdentityRequest(String uuid) {
		this.uuid = uuid;
	}
}