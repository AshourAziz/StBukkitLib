package com.stealthyone.mcb.stbukkitlib.lib.logging;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class LogHelper {

    private LogHelper() { }

    public static void debug(JavaPlugin plugin, String message) {
        if (plugin.getConfig().getBoolean("Debug")) {
            Bukkit.getLogger().log(Level.INFO, String.format("[%s DEBUG] %s", plugin.getName(), message));
        }
    }

    public static void info(JavaPlugin plugin, String message) {
        Bukkit.getLogger().log(Level.INFO, String.format("[%s] %s", plugin.getName(), message));
    }

    public static void warning(JavaPlugin plugin, String message) {
        Bukkit.getLogger().log(Level.WARNING, String.format("[%s] %s", plugin.getName(), message));
    }

    public static void severe(JavaPlugin plugin, String message) {
        Bukkit.getLogger().log(Level.SEVERE, String.format("[%s] %s", plugin.getName(), message));
    }

}