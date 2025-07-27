package com.connectorlib.messages.outbound.old;

import com.connectorlib.BaseOutboundMessage;

import java.util.HashMap;

public class SystemChatData extends BaseOutboundMessage {
	String ip;
	String message;
	HashMap<String, String> recipient = new HashMap<>(1);

	public SystemChatData(String ip, String message, String recipientName, String recipientUuid) {
		this.ip = ip;
		this.message = message;
		this.recipient.put(recipientName, recipientUuid);
	}
}