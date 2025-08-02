package com.connectorlib.messages.outbound;

import com.connectorlib.BaseOutboundMessage;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class PlayerRespawn extends BaseOutboundMessage {
	public ArrayList<Float> position;

	public PlayerRespawn(Vec3d position) {
		this.position = nom(position);
	}

	private ArrayList<Float> nom(Vec3d vec) {
		return new ArrayList<>() {{
			add((float) vec.x);
			add((float) vec.y);
			add((float) vec.z);
		}};
	}
}