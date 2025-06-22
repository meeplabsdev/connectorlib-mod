package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;
import net.minecraft.item.ItemStack;

public class PlayerHotbar extends BaseMessage {
	Integer selectedSlot;
	String[] slotNames = new String[10]; // 0-8: hotbar, 9: offhand
	String[] slotNbts = new String[10];

	public PlayerHotbar(Integer selectedSlot, ItemStack slot1, ItemStack slot2, ItemStack slot3, ItemStack slot4, ItemStack slot5, ItemStack slot6, ItemStack slot7, ItemStack slot8, ItemStack slot9, ItemStack offHand) {
		this.selectedSlot = selectedSlot;
		ItemStack[] slots = {slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, offHand};

		for (int i = 0; i < slots.length; i++) {
			ItemStack slot = slots[i];
			slotNames[i] = slot.getName() != null ? slot.getName().getString() : "";
			slotNbts[i] = (slot.hasNbt() && slot.getNbt() != null) ? slot.getNbt().asString() : "";
		}
	}
}
