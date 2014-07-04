package com.stealthyone.mcb.stbukkitlib.messages;

import com.stealthyone.mcb.stbukkitlib.logging.LogHelper;
import com.stealthyone.mcb.stbukkitlib.storage.YamlFileManager;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * An upgraded version of {@link com.stealthyone.mcb.stbukkitlib.messages.MessageManager} that uses <a href="https://github.com/Stealth2800/MCMarkupLanguage">MCML</a> for
 * more advanced messages.
 */
public class AdvancedMessageManager {

    private JavaPlugin owner;

    /* The file containing the messages */
    private YamlFileManager messageFile;

    /* Message returned for the getMessage methods when the path is invalid */
    private AdvancedMessage unknownMessage;

    /* A list of all loaded messages. Format: <Category, <Name, Message object>> */
    private Map<String, Map<String, AdvancedMessage>> loadedMessages = new HashMap<>();

    private String defaultTag = "&6[{PLUGIN}] &r";

    /* A list of all message tags. Format: <Category, Tag> */
    private Map<String, String> tags = new HashMap<>();

    /**
     * Creates a new instance of the message manager.
     * Uses a file called 'messages.yml' located in the plugin's base directory.
     *
     * @param plugin The plugin that owns the message manager.
     */
    public AdvancedMessageManager(JavaPlugin plugin) {
        this(plugin, plugin.getDataFolder() + File.separator + "messages.yml");
    }

    /**
     * Creates a new instance of the message manager.
     *
     * @param plugin The plugin that owns the message manager.
     * @param filePath The path of the file containing the messages.
     */
    public AdvancedMessageManager(JavaPlugin plugin, String filePath) {
        Validate.notNull(plugin, "Plugin cannot be null.");
        Validate.notNull(filePath, "File path cannot be null.");

        this.owner = plugin;
        this.messageFile = new YamlFileManager(filePath);
        unknownMessage = new AdvancedDummyMessage("&cUnknown message: &4{PATH}&c.");
    }

    public JavaPlugin getOwner() {
        return owner;
    }

    /**
     * Reloads all messages from the messageFile.
     */
    public void reload() {
        tags.clear();
        loadedMessages.clear();

        FileConfiguration config = messageFile.getConfig();

        for (String category : config.getKeys(false)) {
            if (category.equals("tag")) {
                defaultTag = category;
                continue;
            }

            Map<String, AdvancedMessage> messages = new HashMap<>();

            //Load each individual message in the category.
            for (String msgName : config.getConfigurationSection(category).getKeys(false)) {
                if (msgName.equals("tag")) {
                    tags.put(category, config.getString(category + "." + msgName));
                    continue;
                }

                AdvancedMessage newMsg;
                try {
                    newMsg = new AdvancedMessage(this, config.getConfigurationSection(category), msgName);
                } catch (Exception ex) {
                    //An error occurred when trying to create the message object, log and continue
                    LogHelper.warning(owner, "An error occurred while trying to load message '" + msgName + "' in category '" + category + "' in messages.yml for " + owner.getName());
                    ex.printStackTrace();
                    continue;
                }
                messages.put(msgName.toLowerCase(), newMsg);
            }
            loadedMessages.put(category.toLowerCase(), messages);
        }
    }

    String getTag(AdvancedMessage message) {
        return getTag(message.getCategory());
    }

    String getTag(String category) {
        String tag = tags.get(category);
        if (tag == null) tag = defaultTag;
        return tag.replace("{PLUGIN}", owner.getName());
    }

    public AdvancedMessage getMessage(MessagePath messagePath) {
        return getMessage(messagePath.getPath());
    }

    public AdvancedMessage getMessage(String path) {
        String[] pathSplit = path.split("\\.");
        if (pathSplit.length != 2) {
            throw new IllegalArgumentException("Invalid message path format '" + path + "'");
        }

        AdvancedMessage message;
        try {
            message = loadedMessages.get(pathSplit[0]).get(pathSplit[1]);
        } catch (Exception ex) {
            message = null;
        }
        return message == null ? unknownMessage : message;
    }

}