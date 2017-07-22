package net.teamio.oasis;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.teamio.oasis.tileentities.MultiblockProxy;

/**
 * Base class for multiblock patterns.
 * Can be instantiated for simple block patterns, or subclassed for additional custom checks.
 * <p>
 * Created by oliver on 2017-07-22.
 */
public class MultiblockPattern {
	private final int sizeX;
	private final int sizeZ;
	private final Check[] pattern;

	public MultiblockPattern(int sizeX, int sizeZ, Check[] pattern) {
		this.sizeX = sizeX;
		this.sizeZ = sizeZ;
		this.pattern = pattern;
	}

	public boolean check(IBlockAccess world, BlockPos origin, EnumFacing rotation, boolean formed) {
		for (int i = 0; i < pattern.length; i++) {
			Check c = pattern[i];

			// pattern is linear storage of the 3-dimension pattern representation
			int y = i / (sizeX * sizeZ);
			int zx = i % (sizeX * sizeZ);
			int z = zx / sizeX;
			int x = zx % sizeX;

			BlockPos pos = getRotatedOffset(origin, x, y, z, rotation);
			boolean matches;
			if (formed) {
				matches = c.matchesFormed(world, pos, world.getBlockState(pos));
			} else {
				matches = c.matches(world, pos, world.getBlockState(pos));
			}
			if (!matches) {
				return false;
			}
		}
		// Perform custom check
		return customChecks(world, origin, rotation, formed);
	}

	public static BlockPos getRotatedOffset(BlockPos origin, int xOff, int yOff, int zOff, EnumFacing rotation) {
		int x, z;
		switch (rotation) {
			default:
			case SOUTH:
				x = xOff;
				z = zOff;
				break;
			case NORTH:
				x = -xOff;
				z = -zOff;
				break;
			case EAST:
				x = zOff;
				z = xOff;
				break;
			case WEST:
				x = -zOff;
				z = -xOff;
				break;
		}
		return origin.add(x, yOff, z);
	}

	/**
	 * Can be overridden in subclasses for custom checks.
	 * Called after the basic pattern check.
	 *
	 * @param world
	 * @param origin
	 * @return
	 */
	public boolean customChecks(IBlockAccess world, BlockPos origin, EnumFacing rotation, boolean formed) {
		return true;
	}

	public interface Check {
		boolean matches(IBlockAccess world, BlockPos pos, IBlockState state);
		boolean matchesFormed(IBlockAccess world, BlockPos pos, IBlockState state);
	}

	public static class CheckBlock implements Check {
		private final Block block;
		private final int meta;
		private final Class<? extends TileEntity> tileEntityClass;

		public CheckBlock(Block block, int meta) {
			this(block, meta, MultiblockProxy.class);
		}

		public CheckBlock(Block block, int meta, Class<? extends TileEntity> tileEntityClass) {
			this.block = block;
			this.meta = meta;
			this.tileEntityClass = tileEntityClass;
		}

		@Override
		public boolean matches(IBlockAccess world, BlockPos pos, IBlockState state) {
			if (block == null) {
				return state.getBlock().isAir(state, world, pos);
			}
			return state.getBlock() == block && block.getMetaFromState(state) == meta;
		}

		@Override
		public boolean matchesFormed(IBlockAccess world, BlockPos pos, IBlockState state) {
			TileEntity te = world.getTileEntity(pos);
			return te != null && tileEntityClass.isInstance(te);
		}
	}
}
