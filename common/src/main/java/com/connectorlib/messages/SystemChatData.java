package com.connectorlib.messages;

import com.connectorlib.BaseMessage;

public class SystemChatData extends BaseMessage {
	String ip;
	String message;
	String recipient;

	public SystemChatData(String ip, String message, String recipient) {
		this.ip = ip;
		this.message = message;
		this.recipient = recipient;
	}
}