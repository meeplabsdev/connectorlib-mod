package com.connectorlib.messages;

import com.connectorlib.BaseMessage;

public class ChatData extends BaseMessage {
	String ip;
	String message;

	public ChatData(String ip, String message) {
		this.ip = ip;
		this.message = message;
	}
}