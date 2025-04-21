package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;

public class ChatData extends BaseMessage {
	String ip;
	String message;
	String sender;
	String recipient;

	public ChatData(String ip, String message, String sender, String recipient) {
		this.ip = ip;
		this.message = message;
		this.sender = sender;
		this.recipient = recipient;
	}
}