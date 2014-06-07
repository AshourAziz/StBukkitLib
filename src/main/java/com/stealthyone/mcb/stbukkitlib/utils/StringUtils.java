package com.stealthyone.mcb.stbukkitlib.utils;

import java.util.List;

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

    public static String stringListToString(List<String> list) {
        if (list == null) return "";
        StringBuilder sb = new StringBuilder();
        for (String item : list) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(item);
        }
        return sb.toString();
    }

}