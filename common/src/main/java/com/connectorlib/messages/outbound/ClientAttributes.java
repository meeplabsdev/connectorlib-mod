package com.connectorlib.messages.outbound;

import com.connectorlib.BaseOutboundMessage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArmorItem;

import java.util.ArrayList;

public class ClientAttributes extends BaseOutboundMessage {
	public int health;
	public int hunger;
	public int breath;
	public int saturation;
	public int exhaustion;
	public int exp_level;
	public float exp_prog;
	public int protection;
	public String gamemode;
	public int fps;
	public int ping;
	public ArrayList<Object[]> effects;

	public ClientAttributes(MinecraftClient minecraftClient) {
		ClientPlayerEntity player = minecraftClient.player;
		if (player == null) return;

		PlayerInventory inventory = player.getInventory();
		HungerManager hungerManager = player.getHungerManager();
		ClientPlayNetworkHandler nh = player.networkHandler;
		PlayerListEntry entry = nh.getPlayerListEntry(player.getUuid());

		this.health = (int) player.getHealth();
		this.hunger = hungerManager.getFoodLevel();
		this.breath = player.getAir();
		this.saturation = (int) hungerManager.getSaturationLevel();
		this.exhaustion = (int) hungerManager.getExhaustion();
		this.exp_level = player.experienceLevel;
		this.exp_prog = player.experienceProgress;

		this.protection = 0;
		for (int i = 0; i < 4; i++) {
			if (inventory.armor.get(i).getItem() instanceof ArmorItem armorItem) {
				this.protection += armorItem.getProtection();
			}
		}

		if (player.isCreative()) {
			this.gamemode = "creative";
		} else if (player.isSpectator()) {
			this.gamemode = "spectator";
		} else if (player.getAbilities().allowModifyWorld) {
			this.gamemode = "survival";
		} else {
			this.gamemode = "adventure";
		}

		this.fps = minecraftClient.getCurrentFps();
		this.ping = entry != null ? entry.getLatency() : 0;

		this.effects = new ArrayList<>(player.getActiveStatusEffects().size());
		player.getActiveStatusEffects().forEach((statusEffect, effectInstance) -> {
			this.effects.add(new EffectData(effectInstance).ser());
		});
	}

	public static class EffectData {
		String name;
		String type;
		int colour;
		int duration;
		int strength;

		public EffectData(StatusEffectInstance effect) {
			this.name = effect.getEffectType().getTranslationKey();
			this.type = effect.getEffectType().getCategory().name();
			this.colour = effect.getEffectType().getColor();
			this.duration = effect.getDuration();
			this.strength = effect.getAmplifier();
		}

		public Object[] ser() {
			return new Object[]{name, type, colour, duration, strength};
		}
	}
}
