package com.connectorlib.messages;

import com.connectorlib.BaseMessage;

public class SystemChatData extends BaseMessage {
	String ip;
	String message;

	public SystemChatData(String ip, String message) {
		this.ip = ip;
		this.message = message;
	}
}