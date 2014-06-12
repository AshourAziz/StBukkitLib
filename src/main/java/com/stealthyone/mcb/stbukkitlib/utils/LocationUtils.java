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
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Some utility classes for the Bukkit Location class.
 */
public final class LocationUtils {

    private LocationUtils() { }

    /**
     * Converts a location to a string.
     *
     * @param location Location to convert.
     * @param ignoreYawPitch Whether or not to include the 'yaw' and 'pitch' values of the location in the output string.
     * @return Location in string form.
     */
    public static String locationToString(Location location, boolean ignoreYawPitch) {
        Validate.notNull(location, "Location cannot be null.");

        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + (ignoreYawPitch ? "0,0" : location.getYaw() + "," + location.getPitch());
    }

    /**
     * Converts a string to a location.
     * Form: 'worldName,x,y,z,yaw,pitch'
     *
     * @param input String to convert.
     * @return String in location form.
     * @throws java.lang.IllegalArgumentException Thrown if the input string is not a valid location.
     */
    public static Location stringToLocation(String input) {
        Validate.notNull(input, "Input cannot be null.");

        String[] split = input.split(",");
        if (split.length < 6)
            throw new IllegalArgumentException("Invalid input");

        return new Location(
                Bukkit.getWorld(split[0]),
                Double.parseDouble(split[1]),
                Double.parseDouble(split[2]),
                Double.parseDouble(split[3]),
                Float.parseFloat(split[4]),
                Float.parseFloat(split[5])
        );
    }

    /**
     * Converts a list of strings to a list of locations.
     *
     * @param stringList List of strings to convert.
     * @return New list with the converted strings.
     */
    public static List<Location> stringListToLocationList(List<String> stringList) {
        Validate.notNull(stringList, "String list cannot be null.");
        Validate.notEmpty(stringList, "String list cannot be empty.");

        List<Location> locations = new ArrayList<>();
        for (String string : stringList) {
            if (string == null) continue;
            try {
                locations.add(LocationUtils.stringToLocation(string));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return locations;
    }

    /**
     * Converts a list of locations to a list of strings.
     *
     * @param locationList List of locations to convert.
     * @return New list with the converted locations.
     */
    public static List<String> locationListToStringList(List<Location> locationList) {
        List<String> strings = new ArrayList<>();
        for (Location location : locationList) {
            if (location == null) continue;
            strings.add(LocationUtils.locationToString(location, false));
        }
        return strings;
    }

}