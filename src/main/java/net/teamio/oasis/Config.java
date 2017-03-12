package net.teamio.oasis;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

/**
 * Created by oliver on 2017-03-12.
 */
public class Config {

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
