package com.connectorlib.fabric;

import com.connectorlib.ConnectorLibMod;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class PlatformedUpdater extends com.connectorlib.PlatformedUpdater {
	@Override
	public String getCurrentVersion() {
		return FabricLoader.getInstance().getModContainer(ConnectorLibMod.MOD_ID)
			.map(container -> container.getMetadata().getVersion().getFriendlyString())
			.orElse("");
	}

	@Override
	public String getPlatformVersion() {
		return "fabric";
	}

//	@Override
//	public Path getModFilePath() {
//		Path modsFolder = FabricLoader.getInstance().getGameDir().resolve("mods");
//		Optional<ModContainer> currentMod = FabricLoader.getInstance().getModContainer(ConnectorLibMod.MOD_ID);
//		if (currentMod.isPresent()) {
//			String currentModFileName =
//				ConnectorLibMod.MOD_ID + "-" + getPlatformVersion() + "-" + currentMod.get().getMetadata().getVersion().getFriendlyString() +
//					".jar";
//
//			try (DirectoryStream<Path> stream = Files.newDirectoryStream(modsFolder)) {
//				for (Path entry : stream) {
//					if (Files.isRegularFile(entry) && entry.getFileName().toString().equals(currentModFileName)) {
//						return entry.toAbsolutePath();
//					}
//				}
//			} catch (IOException ignored) { }
//		}
//
//		return null;
//	}
}
