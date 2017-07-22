package net.teamio.oasis.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * Proxy TileEntity used in multiblocks.
 * Forwards all capabilities to the multiblock controller TE.
 * <p>
 * Created by oliver on 2017-07-19.
 */
public class MultiblockProxy extends TileEntity {
	BlockPos controller;

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		controller = new BlockPos(nbt.getInteger("controllerX"), nbt.getInteger("controllerY"), nbt.getInteger("controllerZ"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		if (controller != null) {
			nbt.setInteger("controllerX", controller.getX());
			nbt.setInteger("controllerY", controller.getY());
			nbt.setInteger("controllerZ", controller.getZ());
		}
		return nbt;
	}

	public TileEntity getController() {
		return world.getTileEntity(controller);
	}

	public void checkMultiblock() {
		TileEntity controller = getController();
		if (controller instanceof MultiblockController) {
			((MultiblockController) controller).checkMultiblock();
		} else {
			//TODO: what now? Revert?
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side) {
		//TODO: make the capabilities have constraints, where on the multiblock they are available
		TileEntity c = getController();
		if (c == null) {
			return false;
		}
		return c.hasCapability(capability, side);
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side) {
		TileEntity c = getController();
		if (c == null) {
			return null;
		}
		return c.getCapability(capability, side);
	}
}
