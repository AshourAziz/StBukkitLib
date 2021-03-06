package com.stealthyone.mcb.stbukkitlib.messages;

import com.stealthyone.mcb.stbukkitlib.logging.LogHelper;
import com.stealthyone.mcb.stbukkitlib.storage.YamlFileManager;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/*
 * A utility for plugin messages that allows users to modify messages easily.
 */
public class MessageManager {

    private JavaPlugin plugin;

    private YamlFileManager messageFile;

    private Map<String, String> tags = new HashMap<>();
    // Message category, <Message name, Message object>
    private Map<String, Map<String, Message>> loadedMessages = new HashMap<>();

    public MessageManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public JavaPlugin getOwner() {
        return plugin;
    }

    public void reloadMessages() {
        if (messageFile == null) {
            messageFile = new YamlFileManager(plugin.getDataFolder() + File.separator + "messages.yml");
        }
        messageFile.reloadConfig();

        if (messageFile.isEmpty()) {
            plugin.saveResource("messages.yml", true);
        }
        messageFile.copyDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource("messages.yml"))));

        tags.clear();
        loadedMessages.clear();

        FileConfiguration config = messageFile.getConfig();

        // Go through each message category in the message file.
        for (String category : config.getKeys(false)) {
            if (category.equals("tag")) {
                tags.put("default", config.getString(category));
                continue;
            }

            Map<String, Message> messages = new HashMap<>();

            //Load each individual message in the category.
            for (String msgName : config.getConfigurationSection(category).getKeys(false)) {
                if (msgName.equals("tag")) {
                    tags.put(category, config.getString(category + "." + msgName));
                    continue;
                }

                Message newMsg;
                try {
                    newMsg = new Message(this, config.getConfigurationSection(category), msgName);
                } catch (Exception ex) {
                    //An error occurred when trying to create the message object, log and continue
                    LogHelper.warning(plugin, "An error occurred while trying to load message '" + msgName + "' in category '" + category + "' in messages.yml for " + plugin.getName());
                    ex.printStackTrace();
                    continue;
                }
                messages.put(msgName.toLowerCase(), newMsg);
            }
            loadedMessages.put(category.toLowerCase(), messages);
        }

        if (!tags.containsKey("default")) {
            tags.put("default", "&f[&6{PLUGIN}&f] ");
        }
    }

    /**
     * Returns a message with a given path.
     *
     * @param messagePath Path of the message to retrieve.
     *                    The path of the message should be in the format: (category).(message name)
     *                    The message name and category should not have spaces but underscores instead.
     *                    EX: 'errors.no_permission', 'notices.game_saved'
     * @return Message object representing the desired message.
     *         Null if a message at the given path was not found.
     */
    public Message getMessage(String messagePath) {
        Validate.notNull(messagePath, "Message path cannot be null.");

        String[] pathSplit = messagePath.split("\\.");
        if (pathSplit.length != 2) {
            throw new IllegalArgumentException("Invalid message path format '" + messagePath + "'");
        }

        Message message;
        try {
            message = loadedMessages.get(pathSplit[0]).get(pathSplit[1]);
        } catch (Exception ex) {
            message = null;
        }
        return message == null ? createNullMessage(messagePath) : message;
    }

    /**
     * Returns a message with a given path.
     *
     * @param messagePath Implementation of the MessagePath interface.
     * @return Message object representing the desired message.
     *         Null if a message at the given path was not found.
     */
    public Message getMessage(MessagePath messagePath) {
        Validate.notNull(messagePath, "Message path cannot be null.");
        return getMessage(messagePath.getPath());
    }

    private Message createNullMessage(String messageName) {
        return new DummyMessage(this, String.format("{TAG}&4Undefined message '&c%s&4'", messageName));
    }

    protected String getTag(Message message) {
        if (message instanceof DummyMessage) {
            return getTag("default");
        }
        return getTag(message.getCategory());
    }

    protected String getTag(String category) {
        String categoryTag = tags.get(category.toLowerCase());

        String finalTag = categoryTag == null ? tags.get("default") : categoryTag;
        finalTag = finalTag.replace("{PLUGIN}", plugin.getName());
        return finalTag;
    }

}