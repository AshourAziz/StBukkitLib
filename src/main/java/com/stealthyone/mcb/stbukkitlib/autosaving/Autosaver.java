package com.stealthyone.mcb.stbukkitlib.autosaving;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Autosaver implements Runnable {

    private Autosavable autosavable;

    public Autosaver(JavaPlugin plugin, Autosavable autosavable, int minutes) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, minutes * 1200L, minutes * 1200L);
    }

    @Override
    public void run() {
        autosavable.saveAll();
    }

    public static boolean scheduleForMe(JavaPlugin plugin, Autosavable autosavable, int minutes) {
        Validate.notNull(plugin, "Plugin cannot be null.");
        Validate.notNull(autosavable, "Autosavable cannot be null.");

        if (minutes > 0) {
            new Autosaver(plugin, autosavable, minutes);
            return true;
        }
        return false;
    }

}