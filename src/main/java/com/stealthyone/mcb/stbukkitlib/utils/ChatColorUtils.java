package com.stealthyone.mcb.stbukkitlib.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ChatColorUtils {

    private ChatColorUtils() { }

    public static String[] translateAlternateColorCodes(char altColorChar, String[] arrayToTranslate) {
        List<String> returnList = new ArrayList<>();
        for (String string : arrayToTranslate) {
            returnList.add(ChatColor.translateAlternateColorCodes('&', string));
        }
        return returnList.toArray(new String[returnList.size()]);
    }

}