package com.connectorlib;

import com.google.gson.*;
import dev.architectury.platform.Platform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {
	private static final Gson GSON = new GsonBuilder().serializeNulls().create();
	private static ModConfig instance;
	private static JsonObject config;

	private ModConfig() {
		try {
			Path filePath = getConfigFilePath();
			if (Files.exists(filePath)) {
				config = JsonParser.parseString(Files.readString(filePath)).getAsJsonObject();
			} else {
				config = createDefaultConfig();
			}

			config = applyRestrictions(config);
			Files.writeString(filePath, GSON.toJson(config));
		} catch (IOException ignored) {
		}
	}

	private Path getConfigFilePath() {
		String configName = ConnectorLibMod.MOD_ID + "-config.json";
		return Platform.getConfigFolder().resolve(configName).toAbsolutePath();
	}

	private JsonObject createDefaultConfig() {
		JsonObject defaultConfig = new JsonObject();
		defaultConfig.addProperty("analyticsServer", "ws://localhost:3000");
		defaultConfig.addProperty("analyticsPeriod", 10000);
		defaultConfig.addProperty("analyticsEnabled", false);
		return defaultConfig;
	}

	private JsonObject applyRestrictions(JsonObject config) {
		JsonObject restrictions = config.deepCopy();
		float analyticsPeriod = config.get("analyticsPeriod").getAsFloat();
		analyticsPeriod = Math.max(1500, Math.min(6000, analyticsPeriod));
		restrictions.addProperty("analyticsPeriod", analyticsPeriod);

		return restrictions;
	}

	public static ModConfig getInstance() {
		if (instance == null) {
			instance = new ModConfig();
		}
		return instance;
	}

	public JsonElement get(String property) {
		return config.get(property);
	}
}
