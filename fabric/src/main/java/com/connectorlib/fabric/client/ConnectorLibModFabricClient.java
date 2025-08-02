package com.connectorlib.fabric.client;

import com.connectorlib.ModConnector;
import com.connectorlib.messages.outbound.ClientChunk;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;

public final class ConnectorLibModFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientChunkEvents.CHUNK_LOAD.register((world, chunk) -> ModConnector.getInstance().send(new ClientChunk(world, chunk)));
	}
}
