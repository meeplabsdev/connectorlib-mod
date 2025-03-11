package com.connectorlib.forge;

import com.connectorlib.ConnectorLibMod;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class PlatformedUpdater extends com.connectorlib.PlatformedUpdater {
	@Override
	public String getCurrentVersion() {
		return FMLLoader.getLoadingModList().getMods().stream()
			.filter(modInfo -> modInfo.getModId().equals(ConnectorLibMod.MOD_ID))
			.map(modInfo -> modInfo.getVersion().toString())
			.findFirst().orElse("");
	}

	@Override
	public String getPlatformVersion() {
		return "forge";
	}

	@Override
	public Path getModFilePath() {
		Path modsFolder = FMLPaths.MODSDIR.get();
		String currentModFileName = ConnectorLibMod.MOD_ID + "-" + getPlatformVersion() + "-"
			+ ModList.get().getModContainerById(ConnectorLibMod.MOD_ID).get().getModInfo().getVersion() + ".jar";

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(modsFolder)) {
			for (Path entry : stream) {
				if (Files.isRegularFile(entry) && entry.getFileName().toString().equals(currentModFileName)) {
					return entry.toAbsolutePath();
				}
			}
		} catch (Exception ignored) {
		}

		return null;
	}
}
