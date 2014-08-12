package com.stealthyone.mcb.stbukkitlib.utils;

import org.bukkit.configuration.ConfigurationSection;

/**
 * Utility methods for Bukkit's configuration API.
 */
public final class ConfigUtils {

    private ConfigUtils() { }

    /**
     * Returns a nested configuration section with a given name from an existing configuration section.
     *
     * @param from The configuration section that contains the configuration section you're trying to retrieve.
     * @param name The name of the configuration section you're trying to retrieve.
     * @return A configuration section within the 'from' configuration section.<br />
     *         If the configuration section doesn't exist, will create.
     */
    public static ConfigurationSection getSection(ConfigurationSection from, String name) {
        return from.contains(name) ? from.getConfigurationSection(name) : from.createSection(name);
    }

}