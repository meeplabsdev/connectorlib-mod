package com.connectorlib.messages.inbound;

import com.connectorlib.BaseMessage;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.List;

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
		BlockPos pos = mc.player.getBlockPos();

		for (Vec3i position : positions) {
			BlockPos targetPos = pos.add(position);

			if (mc.world.getBlockState(targetPos).getBlock() == Blocks.AIR) {
				Hand hand = mc.player.getActiveHand();
				Vec3d hitPos = targetPos.toCenterPos();

				BlockHitResult hitResult = new BlockHitResult(
					hitPos,
					Direction.UP,
					targetPos.offset(Direction.DOWN),
					false
				);

				mc.interactionManager.interactBlock(mc.player, hand, hitResult);
				mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(hand));
			}
		}
	}
}