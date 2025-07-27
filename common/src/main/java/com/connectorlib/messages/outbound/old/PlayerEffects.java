package com.connectorlib.messages.outbound.old;

import com.connectorlib.BaseOutboundMessage;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.HashMap;
import java.util.List;

public class PlayerEffects extends BaseOutboundMessage {
	HashMap<String, Integer> effects;

	public PlayerEffects(List<StatusEffectInstance> effects) {
		this.effects = new HashMap<>(effects.size());

		for (StatusEffectInstance effect : effects) {
			this.effects.put(effect.getEffectType().getTranslationKey(), effect.getDuration());
		}
	}
}