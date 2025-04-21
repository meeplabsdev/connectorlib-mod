package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class IdentityChallenge extends BaseMessage {
	String result;
	String uuid;

	public IdentityChallenge(String nonce, String uuid) {
		this.authRequired = false;
		this.uuid = uuid;

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest((nonce + "thisIsATestKey!").getBytes(StandardCharsets.UTF_8));

			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}

			result = hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error initializing SHA-256", e);
		}
	}
}