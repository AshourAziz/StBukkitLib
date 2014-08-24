package com.stealthyone.mcb.stbukkitlib.items;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An item that can be loaded via a string.
 */
public class SimpleItem {

    private final static Pattern SPACE_PATTERN = Pattern.compile("(?<!\\\\)_");
    private final static Pattern LIST_SPLIT_PATTERN = Pattern.compile("(?<!\\\\)(,)");
    private final static Pattern FLAG_PATTERN = Pattern.compile("\\s+-(amt|name|lore|enchants)=(\\S+)", Pattern.CASE_INSENSITIVE);

    private Material material;
    private short damage;
    private int amount = 1;

    private String displayName;
    private List<String> lore = new ArrayList<>();
    private Map<Enchantment, Integer> enchantments = new HashMap<>();

    /**
     * Creates an item from a string.<br />
     * Format: ITEM_NAME:DAMAGE -amt=INTEGER -name=STRING -lore=STRING,STRING -enchants=NAME:LEVEL,NAME:LEVEL<br />
     * Spaces in the name and lore sections can be done by putting an underscore (_).  If you want to include an underscore, escape it with \ (\_)<br />
     * You can escape commas in the lore by putting a \ as well, (\,).
     *
     * @param input The string to use.
     * @throws java.lang.RuntimeException Various exceptions that can occur:
     *         <ul>
     *             <li>Illegal material name</li>
     *             <li>Damage isn't a short</li>
     *             <li>Amount isn't an integer</li>
     *         </ul>
     */
    public SimpleItem(@NonNull String input) {
        String firstPart = input.split(" ")[0];

        String[] firstSplit = firstPart.split(":");
        material = Material.valueOf(firstSplit[0].toUpperCase());
        if (firstSplit.length > 1) {
            damage = Short.parseShort(firstSplit[1]);
        } else {
            damage = 0;
        }

        Matcher matcher = FLAG_PATTERN.matcher(input);
        while (matcher.find()) {
            switch (matcher.group(1).toLowerCase()) {
                case "amt":
                    amount = Integer.parseInt(matcher.group(2));
                    break;

                case "name":
                    displayName = polishString(matcher.group(2));
                    break;

                case "lore":
                    readLore(LIST_SPLIT_PATTERN.split(matcher.group(2)));
                    break;

                case "enchants":
                    readEnchants(LIST_SPLIT_PATTERN.split(matcher.group(2)));
                    break;
            }
        }
    }

    private String polishString(String input) {
        return SPACE_PATTERN.matcher(input).replaceAll(" ").replace("\\_", "_").replace("\\,", ",");
    }

    private void readLore(String[] rawInput) {
        for (String string : rawInput) {
            lore.add(polishString(string));
        }
    }

    private void readEnchants(String[] rawInput) {
        for (String string : rawInput) {
            String[] split = string.split(":");
            Enchantment enchant = Enchantment.getByName(split[0].toUpperCase());
            int level;
            if (split.length > 1) {
                level = Integer.parseInt(split[1]);
            } else {
                level = 1;
            }
            enchantments.put(enchant, level);
        }
    }

    /**
     * Builds the item.
     *
     * @return Newly created item.
     */
    public ItemStack getItem() {
        ItemStack item = new ItemStack(material, amount, damage);

        if (displayName != null || !lore.isEmpty() || !enchantments.isEmpty()) {
            ItemMeta meta = Bukkit.getItemFactory().getItemMeta(material);

            if (displayName != null) {
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
            }

            if (!lore.isEmpty()) {
                List<String> newLore = new ArrayList<>();
                for (String line : lore) {
                    newLore.add(ChatColor.translateAlternateColorCodes('&', line));
                }
                meta.setLore(newLore);
            }

            if (!enchantments.isEmpty()) {
                for (Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                    meta.addEnchant(entry.getKey(), entry.getValue(), true);
                }
            }

            item.setItemMeta(meta);
        }

        return item;
    }

}