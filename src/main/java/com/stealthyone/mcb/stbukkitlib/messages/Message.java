package com.stealthyone.mcb.stbukkitlib.messages;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Represents a message.
 */
public class Message {

    private MessageManager messageManager;
    private String category;
    private List<String> messages = new ArrayList<>();

    Message(MessageManager messageManager, ConfigurationSection section, String name) {
        this.messageManager = messageManager;
        if (this instanceof DummyMessage) {
            return;
        }

        category = section.getName();
        Object obj = section.get(name);
        if (obj instanceof String) {
            messages.add((String) obj);
        } else if (obj instanceof List) {
            messages.addAll((List) obj);
        } else {
            throw new IllegalArgumentException("Invalid message value for '" + section.getName() + "." + name + "' in messages.yml for plugin: " + messageManager.getOwner().getName());
        }
    }

    public String getCategory() {
        return category;
    }

    /**
     * Returns all the string messages defined for the message object.
     *
     * @return A list of the messages.
     */
    public List<String> getMessages() {
        return messages;
    }

    /**
     * Returns the messages that would be sent to a player.
     *
     * @return An array of the formatted messages.
     */
    public String[] getFormattedMessages() {
        List<String> returnList = new ArrayList<>();
        for (String msg : messages) {
            if (msg == null || msg.equals("")) continue;
            String finalMsg = msg;
            if (finalMsg.contains("{TAG}")) {
                finalMsg = finalMsg.replace("{TAG}", messageManager.getTag(this));
            }
            returnList.add(ChatColor.translateAlternateColorCodes('&', finalMsg));
        }
        return returnList.toArray(new String[returnList.size()]);
    }

    /**
     * Returns the messages that would be sent to a player.
     *
     * @return An array of the formatted messages with replacements.
     */
    public String[] getFormattedMessages(Map<String, String> replacements) {
        List<String> returnList = new ArrayList<>();
        for (String msg : messages) {
            if (msg == null || msg.equals("")) continue;
            String finalMsg = msg;
            if (finalMsg.contains("{TAG}")) {
                finalMsg = finalMsg.replace("{TAG}", messageManager.getTag(this));
            }
            for (Entry<String, String> replacement : replacements.entrySet()) {
                if (!finalMsg.contains(replacement.getKey())) continue;
                finalMsg = finalMsg.replace(replacement.getKey(), replacement.getValue());
            }
            returnList.add(ChatColor.translateAlternateColorCodes('&', finalMsg));
        }
        return returnList.toArray(new String[returnList.size()]);
    }

    /**
     * Sends the message to a CommandSender.
     *
     * @param sender CommandSender to send all messages in the string list to.
     */
    public void sendTo(CommandSender sender) {
        Validate.notNull(sender, "Sender must not be null.");

        sender.sendMessage(getFormattedMessages());
    }

    /**
     * Sends the message to a CommandSender with a list of replacements.
     *
     * @param sender CommandSender to send all messages in the string list to.
     * @param replacements Replacements to use in the sent message(s).
     *                     EX. "{SENDER}", "Stealth2800"
     */
    public void sendTo(CommandSender sender, Map<String, String> replacements) {
        Validate.notNull(sender, "Sender must not be null.");
        Validate.notNull(sender, "Replacements must not be null.");
        Validate.notEmpty(replacements, "Replacements must not be empty.");

        sender.sendMessage(getFormattedMessages(replacements));
    }

}