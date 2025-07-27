package com.connectorlib.messages.outbound;

import com.connectorlib.BaseOutboundMessage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.message.MessageType;
import net.minecraft.text.Text;

import java.util.List;

public class ChatData extends BaseOutboundMessage {
	public String message;
	public String type;
	public String from;
	public String to;

	public ChatData(MessageType.Parameters parameters, Text message) {
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		ClientPlayNetworkHandler networkHandler = minecraftClient.getNetworkHandler();
		assert networkHandler != null;

		String fromUuid = "";
		Text fromName = parameters.name();
		if (fromName != null) {
			fromUuid = getUuid(networkHandler, fromName.getString());
		}

		String toUuid = "";
		Text toName = parameters.targetName();
		if (toName != null) {
			toUuid = getUuid(networkHandler, toName.getString());
		}

		List<Text> msg = message.withoutStyle();
		this.message = msg.get(msg.size() - 1).getString();
		this.type = parameters.type().chat().translationKey();
		this.from = fromUuid;
		this.to = toUuid;
	}

	private String getUuid(ClientPlayNetworkHandler networkHandler, String name) {
		PlayerListEntry playerListEntry = networkHandler.getPlayerListEntry(name);
		if (playerListEntry != null) {
			return playerListEntry.getProfile().getId().toString().replace("-", "");
		}

		return "";
	}
}