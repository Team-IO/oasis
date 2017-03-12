package net.teamio.oasis.tileentities;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

/**
 * Created by oliver on 2017-03-12.
 */
public class OasisWell extends TileEntity implements ITickable {

	public static final int LUSH_MAX = 3000;
	public static final int LUSH_DECLINE = 1500;


	public static final int WELL_SIZE_X = 3;
	public static final int WELL_SIZE_Z = 3;

	private int lush = LUSH_MAX;

	@Override
	public void update() {
		if (lush > 0) {
			lush--;
		}

		if(lush > 0) {

			float replenishment = 1;
			if(lush < LUSH_DECLINE) {
				replenishment = (float)lush / LUSH_DECLINE;
			}

			updateLush(replenishment);
		}
	}

	private void updateLush(float replenishment) {
		if(world.rand.nextFloat() < replenishment) {
			updateSingleBlock();
		}
	}

	private void updateSingleBlock() {
		for(int x = 0; x < WELL_SIZE_X; x++) {
			for(int z = 0; z < WELL_SIZE_Z; z++) {
				BlockPos p = pos.add(x, 1, z);
				if(world.isAirBlock(p)) {
					world.setBlockState(p, Blocks.WATER.getDefaultState());
				}
				p = pos.add(x, 1, -z);
				if(world.isAirBlock(p)) {
					world.setBlockState(p, Blocks.WATER.getDefaultState());
				}
				p = pos.add(-x, 1, z);
				if(world.isAirBlock(p)) {
					world.setBlockState(p, Blocks.WATER.getDefaultState());
				}
				p = pos.add(-x, 1, -z);
				if(world.isAirBlock(p)) {
					world.setBlockState(p, Blocks.WATER.getDefaultState());
				}
			}
		}
	}
}
