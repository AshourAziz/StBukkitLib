package com.stealthyone.mcb.stbukkitlib.utils;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class FileUtils {

    private FileUtils() { }

    public static File copyFileFromJar(JavaPlugin plugin, String fileName) throws IOException {
        return copyFileFromJar(plugin, fileName, plugin.getDataFolder());
    }

    public static File copyFileFromJar(JavaPlugin plugin, String fileName, File destination) throws IOException {
        Validate.notNull(plugin);
        Validate.notNull(fileName);
        Validate.notNull(destination);
        File file = new File(destination + File.separator + fileName);
        InputStream in = plugin.getResource(fileName);
        if (in == null)
            throw new FileNotFoundException("Unable to find file '" + fileName + "' in jar for plugin: '" + plugin.getName() + "'");

        OutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.close();
        in.close();
        return file;
    }

}