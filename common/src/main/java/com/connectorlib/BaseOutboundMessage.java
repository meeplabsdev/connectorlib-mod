package com.connectorlib;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.architectury.platform.Platform;
import org.msgpack.jackson.dataformat.MessagePackMapper;

public class BaseOutboundMessage extends BaseMessage {
	public byte[] ser() {
		try {
			ObjectMapper objectMapper = new MessagePackMapper();
			return objectMapper.writeValueAsBytes(new MessageWrapper(this));
		} catch (JsonProcessingException e) {
			if (Platform.isDevelopmentEnvironment()) e.printStackTrace();
		}

		return null;
	}
}

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({"id", "value"})
class MessageWrapper {
	public final String id;
	public final BaseMessage value;

	public MessageWrapper(BaseMessage message) {
		this.id = message.getClass().getSimpleName();
		this.value = message;
	}
}
