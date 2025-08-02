package com.connectorlib.messages.outbound;

import com.connectorlib.BaseOutboundMessage;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.Objects;

public class ClientInventory extends BaseOutboundMessage {
	public int slot_selected;
	public ArrayList<Object[]> items;

	public ClientInventory(PlayerInventory inventory) {
		this.slot_selected = inventory.selectedSlot;
		this.items = new ArrayList<>(inventory.size());
		for (int i = 0; i < inventory.size(); i++) {
			ItemStack stack = inventory.getStack(i);
			if (!stack.isEmpty() && !stack.isOf(Items.AIR)) {
				this.items.add(new ItemData(i, stack).ser());
			}
		}
	}

	public static class ItemData {
		String name;
		String custom;
		int slot;
		int count;

		public ItemData(int slot, ItemStack stack) {
			this.name = stack.getTranslationKey();
			this.custom = stack.hasNbt() ? Objects.requireNonNull(stack.getNbt()).asString() : "";
			this.slot = slot;
			this.count = stack.getCount();
		}

		public Object[] ser() {
			return new Object[]{name, custom, slot, count};
		}
	}
}