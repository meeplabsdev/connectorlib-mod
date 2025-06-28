package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.Optional;

public class ChunkData extends BaseMessage {
	String ip;
	String dimension;
	Integer cx;
	Integer cz;

	ArrayList<String> blockKeys = new ArrayList<>();
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
		this.height = chunk.getHeightmap(Heightmap.Type.WORLD_SURFACE).get(0, 0);

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int y = chunk.getHeightmap(Heightmap.Type.WORLD_SURFACE).get(x, z);
				BlockPos bPos = cPos.getBlockPos(x, y - 1, z);
				BlockState bState = world.getBlockState(bPos);
				this.blockKeys.add(bState.getBlock().getTranslationKey());
			}
		}

		Optional<RegistryKey<Biome>> bk = world.getBiome(cPos.getBlockPos(0, this.height, 0)).getKey();
		if (bk.isPresent()) {
			this.biomeKey = bk.get().getValue().toTranslationKey();
		} else {
			biomeKey = "";
		}
	}
}