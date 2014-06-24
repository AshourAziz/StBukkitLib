package com.stealthyone.mcb.stbukkitlib.commands;

import com.stealthyone.mcb.stbukkitlib.StBukkitLib;
import com.stealthyone.mcb.stbukkitlib.logging.LogHelper;
import com.stealthyone.mcb.stbukkitlib.messages.mcml.MCMLBuilder;
import com.stealthyone.mcb.stbukkitlib.utils.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdStBukkitLib implements CommandExecutor {

    private StBukkitLib plugin;

    public CmdStBukkitLib(StBukkitLib plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "message":
                    cmdMessage(sender, args);
                    return true;
            }
        }

        return true;
    }

    private void cmdMessage(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
            return;
        } else if (args.length == 1) {
            sender.sendMessage(ChatColor.RED + "You need to include a message.");
            return;
        }

        String text = ArrayUtils.stringArrayToString(args, 1);
        LogHelper.debug(plugin, "RAWTEXT: " + text);
        MCMLBuilder builder = new MCMLBuilder(text);
        builder.buildFancyMessage().send((Player) sender);
    }

}