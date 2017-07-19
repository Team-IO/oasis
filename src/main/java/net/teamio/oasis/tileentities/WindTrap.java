package net.teamio.oasis.tileentities;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;

/**
 * Created by oliver on 2017-07-19.
 */
public class WindTrap extends MultiblockController implements ITickable {
	private FluidTank tank;
	private int timer;

	public WindTrap() {
		//TODO: capacity in config
		tank = new FluidTank(2000);
	}

	//TODO: read/write NBT

	@Override
	public void update() {
		//TODO: decide if active (sky check, area check)

		timer++;

		int rate = 5;
		int amount = 1;

		if(timer >= rate) {
			timer -= rate;

			fill(amount);
		}
	}

	private void fill(int amount) {
		if(tank.getFluid() == null || tank.getFluid().getFluid() == FluidRegistry.WATER) {
			tank.fillInternal(new FluidStack(FluidRegistry.WATER, amount), true);
		}
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (T)tank;
		}

		return super.getCapability(capability, side);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		}

		return super.hasCapability(capability, side);
	}
}
