package com.connectorlib.messages;

import com.connectorlib.BaseMessage;

public class ChatData extends BaseMessage {
	String ip;
	String message;
	String from;
	String to;

	public ChatData(String ip, String message, String from, String to) {
		this.ip = ip;
		this.message = message;
		this.from = from;
		this.to = to;
	}
}