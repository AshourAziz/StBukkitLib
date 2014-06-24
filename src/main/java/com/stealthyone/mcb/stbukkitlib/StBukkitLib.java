package com.stealthyone.mcb.stbukkitlib;

import com.stealthyone.mcb.stbukkitlib.commands.CmdStBukkitLib;
import org.bukkit.plugin.java.JavaPlugin;

public class StBukkitLib extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("stbukkitlib").setExecutor(new CmdStBukkitLib(this));
        getLogger().info("StBukkitLib loaded successfully.");
    }
}