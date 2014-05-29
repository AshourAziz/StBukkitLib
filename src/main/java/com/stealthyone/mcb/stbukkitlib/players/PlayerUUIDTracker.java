package com.stealthyone.mcb.stbukkitlib.players;

import com.stealthyone.mcb.stbukkitlib.storage.YamlFileManager;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import java.util.UUID;

public class PlayerUUIDTracker {

    private JavaPlugin plugin;
    private YamlFileManager uuidFile;

    private Map<UUID, String> uuidsToNames = new HashMap<>();
    private Map<String, UUID> namesToUuids = new HashMap<>();

    public PlayerUUIDTracker(JavaPlugin plugin) {
        this.plugin = plugin;
        uuidFile = new YamlFileManager(plugin.getDataFolder() + File.separator + "playerUuids.yml");
        createListener();
    }

    public PlayerUUIDTracker(JavaPlugin plugin, File file) {
        this.plugin = plugin;
        uuidFile = new YamlFileManager(file);
        createListener();
    }

    private void createListener() {
        Bukkit.getPluginManager().registerEvents(new PlayerUUIDListener(), plugin);
    }

    public void save() {
        FileConfiguration config = uuidFile.getConfig();
        for (Entry<UUID, String> entry : uuidsToNames.entrySet()) {
            config.set(entry.getKey().toString(), entry.getValue());
        }
        uuidFile.saveFile();
    }

    public void load() {
        FileConfiguration config = uuidFile.getConfig();
        for (String rawUuid : config.getKeys(false)) {
            UUID uuid;
            try {
                uuid = UUID.fromString(rawUuid);
            } catch (Exception ex) {
                plugin.getLogger().severe("Unable to load UUID '" + rawUuid + "' from playerUuids.yml for plugin: '" + plugin.getName() + "'");
                continue;
            }

            String name = config.getString(rawUuid);
            uuidsToNames.put(uuid, name);
            namesToUuids.put(name.toLowerCase(), uuid);
        }
    }

    public UUID getUuid(String name) {
        Validate.notNull(name, "Name cannot be null.");
        return namesToUuids.get(name.toLowerCase());
    }

    public String getName(UUID uuid) {
        Validate.notNull(uuid, "UUID cannot be null.");
        return uuidsToNames.get(uuid);
    }

    public class PlayerUUIDListener implements Listener {

        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent e) {
            UUID uuid = e.getPlayer().getUniqueId();
            String name = e.getPlayer().getName();
            uuidsToNames.put(uuid, name);
            namesToUuids.put(name.toLowerCase(), uuid);
        }

    }

}