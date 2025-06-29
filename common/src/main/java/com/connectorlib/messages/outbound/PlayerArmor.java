package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class PlayerArmor extends BaseMessage {
	Integer protectionLevel;
	HashMap<String, String> slots = new HashMap<>(4);

	public PlayerArmor(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
		this.protectionLevel = 0;

		for (ItemStack item : new ItemStack[]{helmet, chestplate, leggings, boots}) {
			if (item.getItem() instanceof ArmorItem armor) {
				this.protectionLevel += armor.getProtection();
			}

			slots.put(
				(item.getName() != null) ? item.getName().getString() : "",
				(item.hasNbt() && item.getNbt() != null) ? item.getNbt().toString() : ""
			);
		}
	}
}
