package com.stealthyone.mcb.stbukkitlib.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {

    private LocationUtils() { }

    public static String locationToString(Location location) {
        return locationToString(location, false);
    }

    public static String locationToString(Location location, boolean ignoreYawPitch) {
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + (ignoreYawPitch ? "0,0" : location.getYaw() + "," + location.getPitch());
    }

    public static Location stringToLocation(String input) {
        if (input == null)
            throw new IllegalArgumentException("Input cannot be null");

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

}