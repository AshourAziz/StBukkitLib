package com.stealthyone.mcb.stbukkitlib.utils;

public class StringUtils {

    private StringUtils() { }

    public static boolean startsWithMultiple(String string, String... starts) {
        for (String start : starts) {
            if (string.startsWith(start)) {
                return true;
            }
        }
        return false;
    }

}