package com.connectorlib.forge;

import com.connectorlib.ConnectorLibMod;
import com.connectorlib.ModConnector;
import com.connectorlib.messages.outbound.ClientChunk;
import dev.architectury.platform.forge.EventBuses;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ConnectorLibMod.MOD_ID)
public final class ConnectorLibModForge {
	public ConnectorLibModForge() {
		// Submit our event bus to let Architectury API register our content on the right time.
		EventBuses.registerModEventBus(ConnectorLibMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

		// Run our common setup.
		ConnectorLibMod.init();
	}

	@SubscribeEvent
	public static void onChunkLoad(ChunkEvent.Load event) {
		if (event.getLevel().isClient()) {
			ClientWorld world = (ClientWorld) event.getLevel();
			WorldChunk chunk = (WorldChunk) event.getChunk();

			ModConnector.getInstance().send(new ClientChunk(world, chunk));
		}
	}
}
