package com.connectorlib.messages;

import com.connectorlib.BaseMessage;

public class SystemChatData extends BaseMessage {
	String ip;
	String message;
	String to;

	public SystemChatData(String ip, String message, String to) {
		this.ip = ip;
		this.message = message;
		this.to = to;
	}
}