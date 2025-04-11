package com.connectorlib.forge;

import com.connectorlib.ConnectorLibMod;
import net.minecraftforge.fml.loading.FMLLoader;

public class PlatformedUpdater extends com.connectorlib.PlatformedUpdater {
	@Override
	public String getPlatformVersion() {
		return "forge";
	}

	@Override
	public String getCurrentVersion() {
		return FMLLoader.getLoadingModList().getMods().stream()
			.filter(modInfo -> modInfo.getModId().equals(ConnectorLibMod.MOD_ID))
			.map(modInfo -> modInfo.getVersion().toString())
			.findFirst().orElse("");
	}
}
