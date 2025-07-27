package com.connectorlib.messages.outbound;

import com.connectorlib.BaseOutboundMessage;

public class PlayerQuit extends BaseOutboundMessage {
	public String ip;

	public PlayerQuit(String ip) {
		this.ip = ip;
	}
}