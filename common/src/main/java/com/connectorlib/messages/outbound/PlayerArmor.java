package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class PlayerArmor extends BaseMessage {
	Integer protectionLevel;
	String[] itemNames = new String[4];
	String[] itemNbt = new String[4];

	public PlayerArmor(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
		ItemStack[] items = {helmet, chestplate, leggings, boots};
		this.protectionLevel = 0;

		for (int i = 0; i < items.length; i++) {
			ItemStack item = items[i];

			if (item.getItem() instanceof ArmorItem armor) {
				this.protectionLevel += armor.getProtection();
			}

			itemNames[i] = item.getName() != null ? item.getName().getString() : "";
			itemNbt[i] = (item.hasNbt() && item.getNbt() != null) ? item.getNbt().toString() : "";
		}
	}
}
