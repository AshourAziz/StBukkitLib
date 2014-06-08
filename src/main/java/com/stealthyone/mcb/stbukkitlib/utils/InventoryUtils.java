package com.stealthyone.mcb.stbukkitlib.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtils {

    public static void saveToConfigSection(Inventory inventory, ConfigurationSection config) {
        config.set("title", inventory.getName());
        config.set("size", inventory.getSize());
        config.set("items", inventory.getContents());
    }

    public static Inventory loadFromConfigSection(ConfigurationSection config) {
        String title = config.getString("title");
        int size = config.getInt("size");
        Inventory inventory = title.equalsIgnoreCase("") ? Bukkit.createInventory(null, size) : Bukkit.createInventory(null, size, title);

        List<?> list = config.getList("items");
        for (int i = 0; i < size; i++) {
            if (list.get(i) != null)
                inventory.setItem(i, (ItemStack) list.get(i));
        }
        return inventory;
    }

    public static List<ItemStack> getItemstackList(List rawList) {
        List<ItemStack> returnList = new ArrayList<>();
        for (int i = 0; i < rawList.size(); i++) {
            if (rawList.get(i) != null) {
                returnList.add((ItemStack) rawList.get(i));
            } else {
                returnList.add(new ItemStack(Material.AIR));
            }
        }
        return returnList;
    }

}