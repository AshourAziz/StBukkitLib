package com.stealthyone.mcb.stbukkitlib;

import com.stealthyone.mcb.stbukkitlib.items.SimpleItem;
import com.stealthyone.mcb.stbukkitlib.utils.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class StBukkitLib extends JavaPlugin {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args[0].toLowerCase()) {
            case "item":
                cmdItem(sender, args);
                return true;
        }
        return true;
    }

    private void cmdItem(CommandSender sender, String[] args) {
        Player p = ((Player) sender);

        List<String> list = Arrays.asList(args).subList(1, args.length);
        SimpleItem item = new SimpleItem(ArrayUtils.stringArrayToString(list.toArray(new String[list.size()])));
        p.getInventory().addItem(item.getItem());
    }

}