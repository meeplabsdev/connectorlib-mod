package com.connectorlib.messages.outbound.old;

import com.connectorlib.BaseOutboundMessage;

public class PlayerRespawn extends BaseOutboundMessage {
	String ip;
	String uuid;

	public PlayerRespawn(String ip, String uuid) {
		this.ip = ip;
		this.uuid = uuid;
	}
}