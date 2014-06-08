package com.stealthyone.mcb.stbukkitlib.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ArrayUtils {

    private ArrayUtils() { }

    public static String stringArrayToString(String[] input) {
        return stringArrayToString(input, 0);
    }

    public static String stringArrayToString(String[] input, int fromIndex) {
        return stringArrayToString(input, fromIndex, input.length);
    }

    public static String stringArrayToString(String[] input, int fromIndex, int toIndex) {
        StringBuilder sb = new StringBuilder();
        List<String> list = Arrays.asList(input).subList(fromIndex, toIndex);
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String next = it.next();
            sb.append(next);
            if (it.hasNext()) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

}