package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ChunkRequest extends BaseMessage {
	String ip;
	String dimension;
	Integer cx;
	Integer cz;


	public ChunkRequest(String ip, World world, Chunk chunk) {
		this.ip = ip;
		this.dimension = world.getDimensionKey().getValue().toString();

		ChunkPos cPos = chunk.getPos();

		this.cx = cPos.x;
		this.cz = cPos.z;
	}
}