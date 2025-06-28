package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;

import java.util.HashMap;

public class SystemChatData extends BaseMessage {
	String ip;
	String message;
	HashMap<String, String> recipient = new HashMap<>(1);

	public SystemChatData(String ip, String message, String recipientName, String recipientUuid) {
		this.ip = ip;
		this.message = message;
		this.recipient.put(recipientName, recipientUuid);
	}
}