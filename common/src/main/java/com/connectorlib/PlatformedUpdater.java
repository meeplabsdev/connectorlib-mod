package com.connectorlib;

import dev.architectury.platform.Platform;

import java.nio.file.Path;

public class PlatformedUpdater {
	public Path getModFilePath() {
		String modName = ConnectorLibMod.MOD_ID + "-" + getPlatformVersion() + "-" + getCurrentVersion() + ".jar";
		return Platform.getModsFolder().resolve(modName).toAbsolutePath();
	}

	public String getPlatformVersion() {
		return "";
	}

	public String getCurrentVersion() {
		return "";
	}
}
