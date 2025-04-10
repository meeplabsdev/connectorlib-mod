package com.connectorlib.messages;

import com.connectorlib.BaseMessage;

public class IdentityRequest extends BaseMessage {
	String username;
	String uuid;

	public IdentityRequest(String username, String uuid) {
		this.authRequired = false;
		this.username = username;
		this.uuid = uuid;
	}
}