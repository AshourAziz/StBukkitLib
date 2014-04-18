package com.stealthyone.mcb.stbukkitlib.lib.messages;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Represents a message.
 */
public class Message {

    private List<String> messages;

    Message(MessageManager messageManager, ConfigurationSection section, String name) {
        Object obj = section.get(name);
        if (obj instanceof String) {
            messages.add((String) obj);
        } else if (obj instanceof List) {
            messages.addAll((List) obj);
        } else {
            throw new IllegalArgumentException("Invalid message value for '" + section.getName() + "." + name + "' in messages.yml for plugin: " + messageManager.getOwner().getName());
        }
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
     * Sends the message to a CommandSender.
     *
     * @param sender CommandSender to send all messages in the string list to.
     */
    public void sendTo(CommandSender sender) {
        Validate.notNull(sender, "Sender must not be null.");

        for (String msg : messages) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
        }
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

        for (String msg : messages) {
            String finalMsg = msg;
            for (Entry<String, String> replacement : replacements.entrySet()) {
                finalMsg.replace(replacement.getKey(), replacement.getValue());
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', finalMsg));
        }
    }

}