package com.connectorlib.forge;

import com.connectorlib.ConnectorLibMod;
import com.connectorlib.ModUpdater;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.net.URISyntaxException;

@Mod(ConnectorLibMod.MOD_ID)
public final class ConnectorLibModForge {
	public ConnectorLibModForge() {
		// Submit our event bus to let Architectury API register our content on the right time.
		EventBuses.registerModEventBus(ConnectorLibMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

		// Run our common setup.
		try {
			ConnectorLibMod.init();
		} catch (URISyntaxException | InterruptedException e) {
			throw new RuntimeException(e);
		}

		new ModUpdater(new PlatformedUpdater()).update();
	}
}
