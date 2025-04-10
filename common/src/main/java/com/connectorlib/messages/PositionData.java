package com.connectorlib.messages;

import com.connectorlib.BaseMessage;

public class PositionData extends BaseMessage {
	String ip;
	Integer x;
	Integer y;
	Integer z;

	public PositionData(String ip, Integer x, Integer y, Integer z) {
		this.ip = ip;
		this.x = x;
		this.y = y;
		this.z = z;
	}
}