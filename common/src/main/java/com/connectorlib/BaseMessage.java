package com.connectorlib;

import com.google.gson.GsonBuilder;

public abstract class BaseMessage {
	public String id;
	public String session;
	public boolean authRequired = true;

	public BaseMessage() {
		this.id = this.getClass().getSimpleName();
	}

	public String jsonify() {
		return new GsonBuilder().create().toJson(this);
	}
}
