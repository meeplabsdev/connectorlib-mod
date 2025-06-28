package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;

import java.util.ArrayList;
import java.util.List;

public class BulkMessage extends BaseMessage {
	List<BaseMessage> messages = new ArrayList<>();

	public BulkMessage(List<BaseMessage> messages) {
		this.messages = messages;
	}
}