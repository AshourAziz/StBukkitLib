package com.stealthyone.mcb.stbukkitlib.utils;

import java.util.List;

public class MessageUtils {

    public static String stringArrayToString(String[] array) {
        StringBuilder sb = new StringBuilder();
        for (String str : array) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append(str);
        }
        return sb.toString();
    }

    public static String stringListToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append(str);
        }
        return sb.toString();
    }

}