package net.teamio.oasis.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.teamio.oasis.Config;
import net.teamio.oasis.Oasis;

/**
 * TileEntity class for the Oasis Well.
 * Replenishes water source blocks in a configured range above the block.
 * <p>
 * Created by oliver on 2017-03-12.
 */
public class OasisWell extends TileEntity implements ITickable {

	private int wellTimer = Config.well_timer;

	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}
		if (Config.well_count_ticks) {
			if (wellTimer > 0) {
				wellTimer--;
			}
		}

		if (wellTimer > 0) {
			// Replace with water

			if (wellTimer < Config.well_timer_decline) {
				float replenishment = (float) wellTimer / Config.well_timer_decline;

				if (world.rand.nextFloat() < replenishment) {
					replenishSingleBlock();
				}
			} else {
				replenishSingleBlock();
			}

		} else {
			if (Config.well_mud_chance > 0) {
				// Replace with muddy water
				if (world.rand.nextFloat() < Config.well_mud_chance) {
					mudSingleBlock();
				}
			}

		}
	}

	private void replenishSingleBlock() {
		for (int x = 0; x < Config.well_size_x; x++) {
			for (int z = 0; z < Config.well_size_z; z++) {
				if (tryPlaceWater(pos.add(x, 1, z))) return;
				if (tryPlaceWater(pos.add(x, 1, -z))) return;
				if (tryPlaceWater(pos.add(-x, 1, z))) return;
				if (tryPlaceWater(pos.add(-x, 1, -z))) return;
			}
		}
	}

	private void mudSingleBlock() {
		for (int x = 0; x < Config.well_size_x; x++) {
			for (int z = 0; z < Config.well_size_z; z++) {
				if (tryPlaceMud(pos.add(x, 1, z))) return;
				if (tryPlaceMud(pos.add(x, 1, -z))) return;
				if (tryPlaceMud(pos.add(-x, 1, z))) return;
				if (tryPlaceMud(pos.add(-x, 1, -z))) return;
			}
		}
	}

	private boolean tryPlaceMud(BlockPos p) {
		if (canReplaceWithMud(p)) {
			world.setBlockState(p, Oasis.blockMuddyWater.getDefaultState());

			return true;
		}
		return false;
	}

	private boolean tryPlaceWater(BlockPos p) {
		if (canReplaceWithWater(p)) {
			world.setBlockState(p, Blocks.WATER.getDefaultState());

			if (Config.well_count_replenishment > 0) {
				wellTimer -= Config.well_count_replenishment;
			}
			return true;
		}
		return false;
	}

	protected boolean canReplaceWithWater(BlockPos p) {
		if (world.isAirBlock(p)) {
			return true;
		}
		IBlockState state = world.getBlockState(p);
		Block b = state.getBlock();
		if (b == Oasis.blockMuddyWater) {
			return true;
		}
		if (b == Blocks.FLOWING_WATER) {
			int l = state.getValue(BlockLiquid.LEVEL);
			return l != 0;
		}
		return false;
	}

	protected boolean canReplaceWithMud(BlockPos p) {
		IBlockState state = world.getBlockState(p);
		Block b = state.getBlock();
		return b == Blocks.WATER;
	}

}
