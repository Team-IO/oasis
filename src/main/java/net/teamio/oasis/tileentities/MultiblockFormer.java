package net.teamio.oasis.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

/**
 * TileEntity responsible for forming a multiblock.
 * Every one of these will check if they are a suitable controller block.
 * <p>
 * Upon detection, they will replace the other tile entities in the mutliblock with proxies,
 * and replace itself with the appropriate controller.
 * <p>
 * Created by oliver on 2017-07-19.
 */
public class MultiblockFormer extends TileEntity implements ITickable {

	String multiblockType = "";

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		multiblockType = nbt.getString("type");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		if (multiblockType != null) {
			nbt.setString("type", multiblockType);
		}
		return nbt;
	}

	@Override
	public void update() {
		if (multiblockType == null) {
			return;
		}

		BlockPos[] checkPositions = new BlockPos[]{
				pos.up(),
				pos.east(),
				pos.west(),
				pos.north(),
				pos.south(),
		};

		for (BlockPos p : checkPositions) {
			if (!isSameType(p)) {
				return;
			}
		}

		// Form multiblock

		for (BlockPos p : checkPositions) {
			MultiblockProxy proxy = new MultiblockProxy();
			proxy.controller = pos;

			world.setTileEntity(p, proxy);
		}
		//TODO: get pattern & which tileEntity from a list of some kind, depending on multiblock type
		world.setTileEntity(pos, new WindTrap());
	}

	public boolean isSameType(TileEntity te) {
		if (multiblockType == null) {
			return false;
		}
		return te instanceof MultiblockFormer && multiblockType.equals(((MultiblockFormer) te).multiblockType);
	}

	public boolean isSameType(BlockPos pos) {
		return isSameType(world.getTileEntity(pos));
	}
}
