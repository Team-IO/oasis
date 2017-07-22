package net.teamio.oasis.tileentities;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nullable;

/**
 * Created by oliver on 2017-07-22.
 */
public class Miner extends MultiblockController implements ITickable {
	/**
	 * Length of the guide rail
	 */
	public static final int LENGTH = 12;
	/**
	 * Safety distance required from the end of the rail,
	 * so that the drill heads stay clear of the miner itself
	 */
	public static final int SAFETY = 1;

	private EnergyStorage energyStorage;


	public Miner() {
		// Allow a small amount of energy extraction, maybe something will be able to work off the internal storage?
		energyStorage = new EnergyStorage(50000, 500, 50);
	}

	@Override
	public void update() {

	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return (T) energyStorage;
		}
		return super.getCapability(capability, facing);
	}
}
