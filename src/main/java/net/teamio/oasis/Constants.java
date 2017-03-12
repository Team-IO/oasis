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
	public static final String FLUID_MUDDY_WATER = "muddy_water";

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
