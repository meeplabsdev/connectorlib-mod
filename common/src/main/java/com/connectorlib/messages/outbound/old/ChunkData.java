package com.connectorlib.messages.outbound.old;

import com.connectorlib.BaseOutboundMessage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

import java.util.Optional;

public class ChunkData extends BaseOutboundMessage {
	String ip;
	Integer cx;
	Integer cz;
	String dimension;
	String biomeKey;
	int height;


	public ChunkData(String ip, String dimension, Integer cx, Integer cz) {
		this.ip = ip;
		this.dimension = dimension;
		this.cx = cx;
		this.cz = cz;

		ClientPlayerEntity player = MinecraftClient.getInstance().player;
		if (player == null) return;

		World world = player.getWorld();
		if (world == null) return;

		Chunk chunk = world.getChunk(cx, cz);
		ChunkPos cPos = chunk.getPos();
		Optional<RegistryKey<Biome>> bk = world.getBiome(cPos.getBlockPos(0, this.height, 0)).getKey();
		bk.ifPresent(biomeRegistryKey -> this.biomeKey = biomeRegistryKey.getValue().toTranslationKey());
	}
}