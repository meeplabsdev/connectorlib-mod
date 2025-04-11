package com.connectorlib.fabric;

import com.connectorlib.ConnectorLibMod;
import net.fabricmc.loader.api.FabricLoader;

public class PlatformedUpdater extends com.connectorlib.PlatformedUpdater {
	@Override
	public String getPlatformVersion() {
		return "fabric";
	}

	@Override
	public String getCurrentVersion() {
		return FabricLoader.getInstance().getModContainer(ConnectorLibMod.MOD_ID)
			.map(container -> container.getMetadata().getVersion().getFriendlyString())
			.orElse("");
	}
}
