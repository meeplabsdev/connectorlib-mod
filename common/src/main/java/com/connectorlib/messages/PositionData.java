package com.connectorlib.messages;

import com.connectorlib.BaseMessage;

public class PositionData extends BaseMessage {
	String ip;
	String dimension;
	Integer x;
	Integer y;
	Integer z;

	public PositionData(String ip, String dimension, Integer x, Integer y, Integer z) {
		this.ip = ip;
		this.dimension = dimension;
		this.x = x;
		this.y = y;
		this.z = z;
	}
}