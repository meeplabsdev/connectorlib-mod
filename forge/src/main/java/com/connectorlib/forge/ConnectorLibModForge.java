package com.connectorlib.forge;

import com.connectorlib.ConnectorLibMod;
import com.connectorlib.ModConnector;
import com.connectorlib.ModUpdater;
import com.connectorlib.messages.outbound.old.ChunkData;
import dev.architectury.platform.forge.EventBuses;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
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
		new ModUpdater(new PlatformedUpdater()).update();
	}

//	@SubscribeEvent
//	public static void onChunkLoad(ChunkEvent.Load event) {
//		if (event.getLevel().isClient()) {
//			String ip = "unknown";
//			MinecraftClient minecraftClient = MinecraftClient.getInstance();
//			if (minecraftClient.getCurrentServerEntry() != null) {
//				ip = minecraftClient.getCurrentServerEntry().address;
//			}
//
//			ClientWorld world = minecraftClient.world;
//			Chunk chunk = event.getChunk();
//			assert world != null;
//
//			ChunkPos chunkPos = chunk.getPos();
//			ModConnector.getInstance().send(new ChunkData(
//				ip,
//				world.getDimensionKey().getValue().toTranslationKey(),
//				chunkPos.x,
//				chunkPos.z
//			));
//		}
//	}
}
