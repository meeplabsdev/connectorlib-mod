package com.connectorlib.messages.inbound;

import com.connectorlib.BaseMessage;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.List;
import java.util.Objects;

public class SelfTrap extends BaseMessage {
	public SelfTrap() {
		List<Vec3i> positions = List.of(
			new Vec3i(0, -1, 0),
			new Vec3i(1, 0, 0),
			new Vec3i(0, 0, 1),
			new Vec3i(-1, 0, 0),
			new Vec3i(0, 0, -1),
			new Vec3i(1, 1, 0),
			new Vec3i(0, 1, 1),
			new Vec3i(-1, 1, 0),
			new Vec3i(0, 1, -1),
			new Vec3i(0, 2, 0)
		);

		MinecraftClient mc = MinecraftClient.getInstance();
		BlockPos pos = Objects.requireNonNull(mc.player).getBlockPos();

		for (Vec3i position : positions) {
			BlockPos targetPos = pos.add(position);

			if (Objects.requireNonNull(mc.world).getBlockState(targetPos).getBlock() == Blocks.AIR && swapBlockToHotbar(mc.player)) {
				Hand hand = mc.player.getActiveHand();
				Vec3d hitPos = targetPos.toCenterPos();

				BlockHitResult hitResult = new BlockHitResult(
					hitPos,
					Direction.UP,
					targetPos.offset(Direction.DOWN),
					false
				);

				Objects.requireNonNull(mc.interactionManager).interactBlock(mc.player, hand, hitResult);
				Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new HandSwingC2SPacket(hand));
			}
		}
	}

	private Boolean swapBlockToHotbar(PlayerEntity player) {
		ItemStack blockStack = findFirstBlock(player);

		if (blockStack != null && !blockStack.isEmpty()) {
			int hotbarSlot = findEmptyHotbarSlot(player);

			if (hotbarSlot == -1) {
				hotbarSlot = player.getInventory().selectedSlot;
			}

			ItemStack hotbarStack = player.getInventory().getStack(hotbarSlot);
			int blockSlot = findSlotForStack(player, blockStack);

			player.getInventory().setStack(hotbarSlot, blockStack);
			player.getInventory().setStack(blockSlot, hotbarStack);

			player.currentScreenHandler.sendContentUpdates();
			return true;
		}

		return false;
	}

	private ItemStack findFirstBlock(PlayerEntity player) {
		for (int i = 0; i < player.getInventory().size(); i++) {
			ItemStack stack = player.getInventory().getStack(i);
			if (stack.getItem() instanceof BlockItem) {
				return stack;
			}
		}
		return ItemStack.EMPTY;
	}

	private int findEmptyHotbarSlot(PlayerEntity player) {
		for (int i = 0; i < 9; i++) {
			if (player.getInventory().getStack(i).isEmpty()) {
				return i;
			}
		}
		return -1;
	}

	private int findSlotForStack(PlayerEntity player, ItemStack target) {
		for (int i = 0; i < player.getInventory().size(); i++) {
			if (player.getInventory().getStack(i) == target) {
				return i;
			}
		}
		return -1;
	}
}