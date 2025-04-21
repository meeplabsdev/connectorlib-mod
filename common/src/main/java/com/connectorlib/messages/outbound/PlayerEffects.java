package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.ArrayList;
import java.util.List;

public class PlayerEffects extends BaseMessage {
	List<String> effects;
	List<Integer> durations;

	public PlayerEffects(List<StatusEffectInstance> effects) {
		this.effects = new ArrayList<>(effects.size());
		for (StatusEffectInstance effect : effects) {
			this.effects.add(effect.getEffectType().getName().getString());
			this.durations.add(effect.getDuration());
		}
	}
}