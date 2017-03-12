package net.teamio.oasis.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by oliver on 2017-03-12.
 */
public class MuddyWater extends Fluid {
	public MuddyWater(String fluidName) {
		super(fluidName, getResLoc(fluidName + "_still"), getResLoc(fluidName + "_flow"));
		setViscosity(FluidRegistry.WATER.getViscosity());
		setDensity(FluidRegistry.WATER.getDensity());
	}

	private static ResourceLocation getResLoc(String fluidName) {
		return new ResourceLocation("oasis", "blocks/" + fluidName);
	}
}
