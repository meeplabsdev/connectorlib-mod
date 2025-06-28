package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;

import java.util.HashMap;

public class ChatData extends BaseMessage {
	String ip;
	String message;
	HashMap<String, String> sender = new HashMap<>(1);
	HashMap<String, String> recipient = new HashMap<>(1);

	public ChatData(String ip, String message, String senderName, String senderUuid, String recipientName, String recipientUuid) {
		this.ip = ip;
		this.message = message;
		this.sender.put(senderName, senderUuid);
		this.recipient.put(recipientName, recipientUuid);
	}
}