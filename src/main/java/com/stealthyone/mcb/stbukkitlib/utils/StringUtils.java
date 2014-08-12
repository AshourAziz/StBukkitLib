/*
 * StBukkitLib
 * Copyright (C) 2014 Stealth2800 <stealth2800@stealthyone.com>
 * Website: <http://stealthyone.com/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.stealthyone.mcb.stbukkitlib.utils;

import org.apache.commons.lang.Validate;

import java.util.List;

/**
 * Utility methods for Java's String class.
 */
public final class StringUtils {

    private StringUtils() { }

    /**
     * Checks if the input string starts with any of the values.
     *
     * @param string Input string.
     * @param starts Values to check.
     * @return True if the input string starts with any of the given values.
     *         False if none of the values begin the input string.
     */
    public static boolean startsWithMultiple(String string, String... starts) {
        for (String start : starts) {
            if (string.startsWith(start)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts a list of strings into a single string, separated by commas.
     *
     * @param list List to convert to a string.
     * @return Newly created string.
     */
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

    /**
     * Checks if the input string contains any of the values, case sensitive.
     *
     * @param input Input string.
     * @param values List of values to check.
     * @return True if any of the values exist in the input string.<br />
     *         False if none of the values exist in the input string.
     */
    public static boolean containsMultiple(String input, String... values) {
        Validate.notNull(input, "Input cannot be null.");
        Validate.notNull(values, "Values cannot be null.");
        Validate.notEmpty(values, "Values cannot be empty.");

        for (String str : values) {
            if (input.contains(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the input string contains any of the values.
     *
     * @param input Input string.
     * @param values List of values to check.
     * @return True if any of the values exist in the input string.<br />
     *         False if none of the values exist in the input string.
     */
    public static boolean containsMultipleIgnoreCase(String input, String... values) {
        Validate.notNull(input, "Input cannot be null.");
        Validate.notNull(values, "Values cannot be null.");
        Validate.notEmpty(values, "Values cannot be empty.");

        input = input.toLowerCase();
        for (String current : values) {
            if (input.contains(current.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if any of the given values are equal to the input string given.
     *
     * @param input Input string.
     * @param values List of values to compare to the input string.
     * @return True if any of the values are equal.
     *         False if none of the values are equal.
     */
    public static boolean equalsIgnoreCaseMultiple(String input, String... values) {
        Validate.notNull(input, "Input cannot be null.");
        Validate.notNull(values, "Values cannot be null.");
        Validate.notEmpty(values, "Values cannot be empty.");

        for (String string : values) {
            if (input.equalsIgnoreCase(string)) {
                return true;
            }
        }
        return false;
    }

}