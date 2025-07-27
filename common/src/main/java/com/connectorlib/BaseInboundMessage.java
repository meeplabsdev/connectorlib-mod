package com.connectorlib;

public class BaseInboundMessage extends BaseMessage {
	protected BaseOutboundMessage response;

	public void respond() {
		if (response != null) {
			ModConnector connector = ModConnector.getInstance();
			connector.send(response);
		}
	}
}
