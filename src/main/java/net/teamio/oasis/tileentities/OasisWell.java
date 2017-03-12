package net.teamio.oasis.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.teamio.oasis.Oasis;

/**
 * Created by oliver on 2017-03-12.
 */
public class OasisWell extends TileEntity implements ITickable {

	public static final int LUSH_MAX = 3000000;
	public static final int LUSH_DECLINE = 1500;
	public static final float MUD_CHANCE = 0.2f;
	/**
	 * Lush timer ticks down this amount when a water block is placed.
	 */
	public static final int LUSH_TICK_PLACEMENT = 150;


	public static final int WELL_SIZE_X = 3;
	public static final int WELL_SIZE_Z = 3;

	private int lush = LUSH_MAX;

	@Override
	public void update() {
		if(world.isRemote) {
			return;
		}
		if (lush > 0) {
			lush--;
		}

		if(lush > 0) {
			// Replace with water
			float replenishment = 1;
			if(lush < LUSH_DECLINE) {
				replenishment = (float)lush / LUSH_DECLINE;
			}

			if(world.rand.nextFloat() < replenishment) {
				replenishSingleBlock();
			}
		} else {
			// Replace with muddy water
			if(world.rand.nextFloat() < MUD_CHANCE) {
				mudSingleBlock();
			}

		}
	}

	private void replenishSingleBlock() {
		for(int x = 0; x < WELL_SIZE_X; x++) {
			for(int z = 0; z < WELL_SIZE_Z; z++) {
				if(tryPlaceWater(pos.add(x, 1, z))) return;
				if(tryPlaceWater(pos.add(x, 1, -z))) return;
				if(tryPlaceWater(pos.add(-x, 1, z))) return;
				if(tryPlaceWater(pos.add(-x, 1, -z))) return;
			}
		}
	}

	private void mudSingleBlock() {
		for(int x = 0; x < WELL_SIZE_X; x++) {
			for(int z = 0; z < WELL_SIZE_Z; z++) {
				if(tryPlaceMud(pos.add(x, 1, z))) return;
				if(tryPlaceMud(pos.add(x, 1, -z))) return;
				if(tryPlaceMud(pos.add(-x, 1, z))) return;
				if(tryPlaceMud(pos.add(-x, 1, -z))) return;
			}
		}
	}

	private boolean tryPlaceMud(BlockPos p) {
		if(canReplaceWithMud(p)) {
			world.setBlockState(p, Oasis.blockMuddyWater.getDefaultState());

			return true;
		}
		return false;
	}

	private boolean tryPlaceWater(BlockPos p) {
		if(canReplaceWithWater(p)) {
			world.setBlockState(p, Blocks.WATER.getDefaultState());

			lush -= LUSH_TICK_PLACEMENT;
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
		if(b == Oasis.blockMuddyWater) {
			return true;
		}
		if(b == Blocks.FLOWING_WATER) {
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
