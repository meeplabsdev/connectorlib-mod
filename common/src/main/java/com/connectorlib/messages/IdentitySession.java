package com.connectorlib.messages;

import com.connectorlib.BaseMessage;
import com.connectorlib.ModConnector;

public class IdentitySession extends BaseMessage {
	public IdentitySession(String session) {
		this.authRequired = false;
		ModConnector.getInstance().setSession(session);
	}
}