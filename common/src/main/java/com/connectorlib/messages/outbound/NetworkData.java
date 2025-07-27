package com.connectorlib.messages.outbound;

import com.connectorlib.BaseOutboundMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

public class NetworkData extends BaseOutboundMessage {
	public String ip;
	public String user_agent;
	public String via;
	public String forwarded;

	public NetworkData() {
		try {
			URL url = new URI("https://ifconfig.me/all.json").toURL();
			URLConnection connection = url.openConnection();
			StringBuilder sb = new StringBuilder();

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			}

			JsonObject jsonObject = new Gson().fromJson(sb.toString(), JsonObject.class);
			this.ip = jsonObject.has("ip_addr") ? jsonObject.get("ip_addr").getAsString() : "";
			this.user_agent = jsonObject.has("user_agent") ? jsonObject.get("user_agent").getAsString() : "";
			this.via = jsonObject.has("via") ? jsonObject.get("via").getAsString() : "";
			this.forwarded = jsonObject.has("forwarded") ? jsonObject.get("forwarded").getAsString() : "";
		} catch (IOException | URISyntaxException ignored) {
		}
	}
}