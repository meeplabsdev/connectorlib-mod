package com.connectorlib;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;

public class ModUpdater {
	PlatformedUpdater platformedUpdater;

	public ModUpdater(PlatformedUpdater platformedUpdater) {
		this.platformedUpdater = platformedUpdater;
	}

	public void update() {
		String currentVersion = this.platformedUpdater.getCurrentVersion();
		String remoteUrl = getRemoteUrl(currentVersion);

		if (!Objects.equals(remoteUrl, "")) {
			Path modPath = this.platformedUpdater.getModFilePath();
			if (modPath != null) {
				try {
					downloadFile(remoteUrl, modPath.toAbsolutePath().toString());
				} catch (IOException ignored) {
				}
			}
		}
	}

	private String getRemoteUrl(String currentVersion) {
		try {
			URL url = new URL("https://api.github.com/repos/" + ConnectorLibMod.ghUser + "/" + ConnectorLibMod.repoName + "/releases/latest");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();
			conn.disconnect();

			JsonObject releases = JsonParser.parseString(response.toString()).getAsJsonObject();
			JsonArray assets = releases.get("assets").getAsJsonArray();
			String tag_name = releases.get("tag_name").getAsString();

			for (JsonElement asset : assets) {
				JsonObject assetObj = asset.getAsJsonObject();
				String name = assetObj.get("name").getAsString();
				String dl_url = assetObj.get("browser_download_url").getAsString();

				int tag_int = Integer.parseInt(tag_name.replaceAll("[^0-9]", ""));
				int cur_int = Integer.parseInt(currentVersion.replaceAll("[^0-9]", ""));

				if (name.endsWith(".jar") && name.contains(this.platformedUpdater.getPlatformVersion()) && cur_int < tag_int) {
					return dl_url;
				}
			}

		} catch (IOException ignored) {
		}

		return "";
	}

	private void downloadFile(String fileURL, String savePath) throws IOException {
		URL url = new URL(fileURL);
		BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
		FileOutputStream outputStream = new FileOutputStream(savePath);

		byte[] buffer = new byte[1024];
		int bytesRead;

		while ((bytesRead = inputStream.read(buffer, 0, 1024)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}

		outputStream.close();
		inputStream.close();
	}
}
