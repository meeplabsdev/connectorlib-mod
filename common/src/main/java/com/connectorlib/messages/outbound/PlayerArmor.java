package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class PlayerArmor extends BaseMessage {
	Integer protectionLevel;
	String helmetName;
	String helmetNbt;
	String chestplateName;
	String chestplateNbt;
	String leggingsName;
	String leggingsNbt;
	String bootsName;
	String bootsNbt;

	public PlayerArmor(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
		this.protectionLevel = 0;

		if (helmet.getItem() instanceof ArmorItem helmetArmor) {
			this.protectionLevel += helmetArmor.getProtection();
		}

		if (chestplate.getItem() instanceof ArmorItem chestplateArmor) {
			this.protectionLevel += chestplateArmor.getProtection();
		}

		if (leggings.getItem() instanceof ArmorItem leggingsArmor) {
			this.protectionLevel += leggingsArmor.getProtection();
		}

		if (boots.getItem() instanceof ArmorItem bootsArmor) {
			this.protectionLevel += bootsArmor.getProtection();
		}

		this.helmetName = helmet.getName().getString();
		this.chestplateName = chestplate.getName().getString();
		this.leggingsName = leggings.getName().getString();
		this.bootsName = boots.getName().getString();

		if (helmet.hasNbt()) {
			assert helmet.getNbt() != null;
			this.helmetNbt = helmet.getNbt().toString();
		}

		if (chestplate.hasNbt()) {
			assert chestplate.getNbt() != null;
			this.chestplateNbt = chestplate.getNbt().toString();
		}

		if (leggings.hasNbt()) {
			assert leggings.getNbt() != null;
			this.leggingsNbt = leggings.getNbt().toString();
		}

		if (boots.hasNbt()) {
			assert boots.getNbt() != null;
			this.bootsNbt = boots.getNbt().toString();
		}
	}
}