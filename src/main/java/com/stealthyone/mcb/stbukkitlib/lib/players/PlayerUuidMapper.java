package com.stealthyone.mcb.stbukkitlib.lib.players;

import com.stealthyone.mcb.stbukkitlib.lib.storage.YamlFileManager;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class PlayerUuidMapper {

    private YamlFileManager uuidFile;
    private Map<UUID, String> uuidsToNames = new HashMap<>();
    private Map<String, UUID> namesToUuids = new HashMap<>();

    public PlayerUuidMapper(YamlFileManager file) {
        Validate.notNull(file, "File must not be null.");

        uuidFile = file;
        reload();
    }

    public PlayerUuidMapper(File file) {
        Validate.notNull(file, "File must not be null.");

        uuidFile = new YamlFileManager(file);
        reload();
    }

    public PlayerUuidMapper(String filePath) {
        Validate.notNull(filePath, "File path must not be null.");
    }

    public void reload() {
        uuidFile.reloadConfig();
        uuidsToNames.clear();
        namesToUuids.clear();

        FileConfiguration uuidConfig = uuidFile.getConfig();
        for (String rawUuid : uuidConfig.getKeys(false)) {
            UUID uuid = UUID.fromString(rawUuid);
            String name = uuidConfig.getString(rawUuid);
            uuidsToNames.put(uuid, name);
            namesToUuids.put(name.toLowerCase(), uuid);
        }
    }

    public void save() {
        FileConfiguration uuidConfig = uuidFile.getConfig();
        for (Entry<UUID, String> entry : uuidsToNames.entrySet()) {
            uuidConfig.set(entry.getKey().toString(), entry.getValue());
        }
        uuidFile.saveFile();
    }

    public void updatePlayer(Player player) {
        UUID uuid = player.getUniqueId();
        String name = player.getName();

        uuidsToNames.put(uuid, name);
        namesToUuids.put(name.toLowerCase(), uuid);
    }

}