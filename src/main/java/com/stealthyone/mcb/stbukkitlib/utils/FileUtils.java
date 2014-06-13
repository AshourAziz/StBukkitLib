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
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

/**
 * Utility methods for Java's File class in conjunction with Bukkit.
 */
public class FileUtils {

    private FileUtils() { }

    public static File copyFileFromJar(JavaPlugin plugin, String fileName) throws IOException {
        return copyFileFromJar(plugin, fileName, plugin.getDataFolder());
    }

    public static File copyFileFromJar(JavaPlugin plugin, String fileName, File destination) throws IOException {
        Validate.notNull(plugin, "Plugin cannot be null.");
        Validate.notNull(fileName, "File name cannot be null.");
        Validate.notNull(destination, "Destination cannot be null.");
        
        InputStream in = plugin.getResource(fileName);
        if (in == null)
            throw new FileNotFoundException("Unable to find file '" + fileName + "' in jar for plugin: '" + plugin.getName() + "'");

        OutputStream out = new FileOutputStream(destination);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.close();
        in.close();
        return destination;
    }

}