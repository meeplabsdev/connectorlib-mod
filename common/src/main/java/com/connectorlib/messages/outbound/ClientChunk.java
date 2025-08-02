package com.connectorlib.messages.outbound;

import com.connectorlib.BaseOutboundMessage;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;

import java.util.ArrayList;
import java.util.List;

public class ClientChunk extends BaseOutboundMessage {
	public String dimension;
	public String biome;
	public int cx;
	public int cz;
	public ArrayList<Integer> ch;

	public ClientChunk(ClientWorld world, WorldChunk chunk) {
		RegistryKey<DimensionType> dimension = world.getDimensionKey();
		this.dimension = dimension.getValue().toTranslationKey();

		this.cx = chunk.getPos().x;
		this.cz = chunk.getPos().z;

		this.ch = new ArrayList<>(4);
		List.of(Type.WORLD_SURFACE, Type.OCEAN_FLOOR, Type.MOTION_BLOCKING, Type.MOTION_BLOCKING_NO_LEAVES).forEach(hm -> {
			this.ch.add(chunk.sampleHeightmap(hm, 0, 0));
		});

		RegistryKey<Biome> biome = world.getBiome(chunk.getPos().getBlockPos(0, this.ch.get(0), 0)).getKey().orElse(null);
		if (biome != null) {
			this.biome = biome.getValue().toTranslationKey();
		} else {
			this.biome = "unknown";
		}
	}
}