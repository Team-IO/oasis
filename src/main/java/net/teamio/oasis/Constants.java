package net.teamio.oasis;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

/**
 * Created by oliver on 2017-03-12.
 */
public class Constants {
	public static final String MOD_ID = "oasis";
	public static final String MOD_NAME = "Oasis";
	public static final String MOD_VERSION = "@VERSION@";

	public static final String BLOCK_MACHINE = "machine";
	public static final String BLOCK_MULTIBLOCK = "multiblock";
	public static final String FLUID_MUDDY_WATER = "muddy_water";

	public static final String TILEENTITY_OASIS_WELL = "oasis.well";
	public static final String TILEENTITY_MULTIBLOCK_FORMER = "oasis.multiblock_former";
	public static final String TILEENTITY_MULTIBLOCK_PROXY = "oasis.multiblock_proxy";
	public static final String TILEENTITY_MB_WIND_TRAP = "oasis.multiblock.wind_trap";

	public enum BLOCK_MACHINES_META implements IStringSerializable {
		OasisWell,
		WellPump;

		public static String[] valuesAsString() {
			Enum<?>[] valuesAsEnum = values();
			String[] valuesAsString = new String[valuesAsEnum.length];
			for (int i = 0; i < valuesAsEnum.length; i++) {
				valuesAsString[i] = valuesAsEnum[i].name();
			}
			return valuesAsString;
		}

		@Nonnull
		@Override
		public String getName() {
			return name();
		}
	}
}
