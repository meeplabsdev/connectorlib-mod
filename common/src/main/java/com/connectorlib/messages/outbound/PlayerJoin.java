package com.connectorlib.messages.outbound;

import com.connectorlib.BaseOutboundMessage;

public class PlayerJoin extends BaseOutboundMessage {
	public String ip;

	public PlayerJoin(String ip) {
		this.ip = ip;
	}
}