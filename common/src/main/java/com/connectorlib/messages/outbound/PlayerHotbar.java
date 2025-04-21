package com.connectorlib.messages.outbound;

import com.connectorlib.BaseMessage;
import net.minecraft.item.ItemStack;

public class PlayerHotbar extends BaseMessage {
	Integer selectedSlot;
	String slot1Name;
	String slot1Nbt;
	String slot2Name;
	String slot2Nbt;
	String slot3Name;
	String slot3Nbt;
	String slot4Name;
	String slot4Nbt;
	String slot5Name;
	String slot5Nbt;
	String slot6Name;
	String slot6Nbt;
	String slot7Name;
	String slot7Nbt;
	String slot8Name;
	String slot8Nbt;
	String slot9Name;
	String slot9Nbt;
	String slot10Name;
	String slot10Nbt;

	public PlayerHotbar(Integer selectedSlot, ItemStack slot1, ItemStack slot2, ItemStack slot3, ItemStack slot4, ItemStack slot5, ItemStack slot6, ItemStack slot7, ItemStack slot8, ItemStack slot9, ItemStack offHand) {
		this.selectedSlot = selectedSlot;
		this.slot1Name = slot1.getName().getString();
		this.slot2Name = slot2.getName().getString();
		this.slot3Name = slot3.getName().getString();
		this.slot4Name = slot4.getName().getString();
		this.slot5Name = slot5.getName().getString();
		this.slot6Name = slot6.getName().getString();
		this.slot7Name = slot7.getName().getString();
		this.slot8Name = slot8.getName().getString();
		this.slot9Name = slot9.getName().getString();
		this.slot10Name = offHand.getName().getString();

		if (slot1.hasNbt()) {
			assert slot1.getNbt() != null;
			this.slot1Nbt = slot1.getNbt().asString();
		}

		if (slot2.hasNbt()) {
			assert slot2.getNbt() != null;
			this.slot2Nbt = slot2.getNbt().asString();
		}

		if (slot3.hasNbt()) {
			assert slot3.getNbt() != null;
			this.slot3Nbt = slot3.getNbt().asString();
		}

		if (slot4.hasNbt()) {
			assert slot4.getNbt() != null;
			this.slot4Nbt = slot4.getNbt().asString();
		}

		if (slot5.hasNbt()) {
			assert slot5.getNbt() != null;
			this.slot5Nbt = slot5.getNbt().asString();
		}

		if (slot6.hasNbt()) {
			assert slot6.getNbt() != null;
			this.slot6Nbt = slot6.getNbt().asString();
		}

		if (slot7.hasNbt()) {
			assert slot7.getNbt() != null;
			this.slot7Nbt = slot7.getNbt().asString();
		}

		if (slot8.hasNbt()) {
			assert slot8.getNbt() != null;
			this.slot8Nbt = slot8.getNbt().asString();
		}

		if (slot9.hasNbt()) {
			assert slot9.getNbt() != null;
			this.slot9Nbt = slot9.getNbt().asString();
		}

		if (offHand.hasNbt()) {
			assert offHand.getNbt() != null;
			this.slot10Nbt = offHand.getNbt().asString();
		}
	}
}