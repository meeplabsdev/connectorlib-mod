package com.connectorlib.messages.inbound;

import com.connectorlib.BaseInboundMessage;
import com.connectorlib.ModConfig;
import com.connectorlib.messages.outbound.IdentityChallenge;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import dev.architectury.platform.Platform;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Random;

public class IdentityRequest extends BaseInboundMessage {
	@JsonCreator
	public IdentityRequest(@JsonProperty("nonce") String nonce, @JsonProperty("iv") String iv) {
		MinecraftClient mc = MinecraftClient.getInstance();
		String plaintext = nonce + mc.getSession().getUuid().replaceAll("-", "");
		String authenticity = HexFormat.of().formatHex(encrypt(plaintext, ModConfig.getInstance().get("encryptionKey").getAsString(), iv));

		String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuilder sb = new StringBuilder(40);
		for (int i = 0; i < 40; i++) sb.append(chars.charAt(random.nextInt(chars.length())));
		String serverId = sb.toString();

		try {
			MinecraftClient minecraftClient = MinecraftClient.getInstance();
			Session session = minecraftClient.getSession();
			MinecraftSessionService sessionService = minecraftClient.getSessionService();
			sessionService.joinServer(session.getProfile(), session.getAccessToken(), serverId);
		} catch (AuthenticationException ignored) {}

		this.response = new IdentityChallenge(authenticity, serverId);
	}

	private byte[] encrypt(String _plaintext, String _key, String _iv) {
		byte[] plaintext = _plaintext.getBytes();
		byte[] key = _key.getBytes();
		byte[] iv = _iv.getBytes();

		SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
			return cipher.doFinal(plaintext);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			if (Platform.isDevelopmentEnvironment()) e.printStackTrace();
			return new byte[0];
		}
	}
}