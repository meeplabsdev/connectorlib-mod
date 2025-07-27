package com.connectorlib.messages.outbound.old;

import com.connectorlib.BaseOutboundMessage;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class PlayerHotbar extends BaseOutboundMessage {
	Integer selectedSlot;
	HashMap<String, String> slots = new HashMap<>(10);

	public PlayerHotbar(Integer selectedSlot, ItemStack[] slots) {
		this.selectedSlot = selectedSlot;

		for (ItemStack slot : slots) {
			this.slots.put(
				(slot.getName() != null) ? slot.getName().getString() : "",
				(slot.hasNbt() && slot.getNbt() != null) ? slot.getNbt().asString() : ""
			);
		}
	}
}
