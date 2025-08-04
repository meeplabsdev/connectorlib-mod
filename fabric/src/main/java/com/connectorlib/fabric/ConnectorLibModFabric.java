package com.connectorlib.fabric;

import com.connectorlib.ConnectorLibMod;
import net.fabricmc.api.ModInitializer;

public final class ConnectorLibModFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		// Run our common setup.
		ConnectorLibMod.init();
	}
}
