package net.teamio.oasis;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.io.InterruptedIOException;

/**
 * Created by oliver on 2017-03-12.
 */
public class Config {

	public static int well_size_x;
	public static int well_size_z;
	public static int well_timer;
	public static int well_timer_decline;

	public static boolean well_count_ticks;
	public static int well_count_replenishment;

	public static float well_mud_chance;

	public static Configuration config;

	public static void init(File configFile) {


		if (config == null) {
			config = new Configuration(configFile);

			loadConfig();
		}

	}

	private static int getInt(String name, String category, int defaultValue, int minValue, int maxValue, String comment) {
		String langKey = String.format("oasis.config.%s.%s", category, name);
		return config.getInt(name, category, defaultValue, minValue, maxValue, comment, langKey);
	}

	/**
	 * Sets needsWorldRestart
	 *
	 * @param name
	 * @param category
	 * @param defaultValue
	 * @param minValue
	 * @param maxValue
	 * @param comment
	 * @return
	 */
	private static int getIntWR(String name, String category, int defaultValue, int minValue, int maxValue, String comment) {
		String langKey = String.format("oasis.config.%s.%s", category, name);
		Property prop = config.get(category, name, defaultValue, comment, minValue, maxValue);
		prop.setLanguageKey(langKey);
		prop.setRequiresWorldRestart(true);
		return prop.getInt();
	}

	private static float getFloat(String name, String category, float defaultValue, float minValue, float maxValue, String comment) {
		String langKey = String.format("oasis.config.%s.%s", category, name);
		return config.getFloat(name, category, defaultValue, minValue, maxValue, comment, langKey);
	}

	private static byte getByte(String name, String category, int defaultValue, int minValue, int maxValue, String comment) {
		String langKey = String.format("oasis.config.%s.%s", category, name);
		return (byte) config.getInt(name, category, defaultValue, minValue, maxValue, comment, langKey);
	}

	private static boolean getBoolean(String name, String category, boolean defaultValue, String comment) {
		String langKey = String.format("oasis.config.%s.%s", category, name);
		return config.getBoolean(name, category, defaultValue, comment, langKey);
	}

	private static void loadConfig() {

		well_size_x = getInt("size_x", "oasis_well", 3, 1, 300, "Size of Oasis Wells on the X axis. (Radius, actual well size will be size_x * 2 + 1)");
		well_size_z = getInt("size_z", "oasis_well", 3, 1, 300, "Size of Oasis Wells on the X axis. (Radius, actual well size will be size_x * 2 + 1)");
		well_timer = getInt("timer", "oasis_well", 3000000, 500, Integer.MAX_VALUE, "Timeout until a well dries out. Depending on the config, either time, replenishing water sources, or both will affect this.");
		well_timer_decline = getInt("timer_decline", "oasis_well", 1500, 0, Integer.MAX_VALUE, "Configure a slow decline in effectiveness instead of immediately drying out. If the timer of a well drops below this amount, replenishment slows down linearly. Chance for replenishment is calculated by remaining_timer / well_timer_decline.");

		well_count_ticks = getBoolean("count_ticks", "oasis_well", true, "Enable or disable tick counting. If enabled, the timer of a well is decreased by 1 in every tick.");
		well_count_replenishment = getInt("count_replenishment", "oasis_well", 150, 0, Integer.MAX_VALUE, "Configure replenishment counting. If >0, replenishing a water source block will decrease the timer by the defined amount.");

		well_mud_chance = getFloat("mud_change", "oasis_well", 0.2f, 0, 1f, "Chance of water source blocks turning into muddy water when a well is dried out. Pass 0 to disable.");

		if (config.hasChanged()) {
			config.save();
		}
	}

	@SubscribeEvent
	public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equalsIgnoreCase(Constants.MOD_ID)) {
			loadConfig();
		}
	}
}
