package com.connectorlib.fabric.client;

import com.connectorlib.ModConnector;
import com.connectorlib.messages.outbound.old.ChunkData;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.ChunkPos;

public final class ConnectorLibModFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
//		ClientChunkEvents.CHUNK_LOAD.register((world, chunk) -> {
//			String ip = "unknown";
//			MinecraftClient minecraftClient = MinecraftClient.getInstance();
//			if (minecraftClient.getCurrentServerEntry() != null) {
//				ip = minecraftClient.getCurrentServerEntry().address;
//			}
//
//			ChunkPos chunkPos = chunk.getPos();
//			ModConnector.getInstance().send(new ChunkData(
//				ip,
//				world.getDimensionKey().getValue().toTranslationKey(),
//				chunkPos.x,
//				chunkPos.z
//			));
//		});
	}
}
