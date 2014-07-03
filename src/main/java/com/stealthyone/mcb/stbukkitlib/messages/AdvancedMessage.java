package com.stealthyone.mcb.stbukkitlib.messages;

import com.stealthyone.mcb.mcml.MCMLBuilder;
import mkremins.fanciful.FancyMessage;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AdvancedMessage {

    private AdvancedMessageManager messageManager;
    private String category;
    private List<String> messages = new ArrayList<>();

    AdvancedMessage(AdvancedMessageManager messageManager, ConfigurationSection section, String name) {
        this.messageManager = messageManager;
        if (this instanceof AdvancedDummyMessage) return;

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
     * Returns all the raw string messages defined for the message object.
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
    public List<FancyMessage> getFormattedMessages() {
        List<FancyMessage> returnList = new ArrayList<>();
        for (String msg : messages) {
            if (msg == null || msg.equals("")) continue;
            String finalMsg = msg;
            if (finalMsg.contains("{TAG}")) {
                finalMsg = finalMsg.replace("{TAG}", messageManager.getTag(this));
            }
            returnList.add(new MCMLBuilder(finalMsg).buildFancyMessage());
        }
        return returnList;
    }

    /**
     * Returns the messages that would be sent to a player.
     *
     * @return An array of the formatted messages with replacements.
     */
    public List<FancyMessage> getFormattedMessages(Map<String, String> replacements) {
        List<FancyMessage> returnList = new ArrayList<>();
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
            returnList.add(new MCMLBuilder(finalMsg).buildFancyMessage());
        }
        return returnList;
    }

    /**
     * Sends the message to a CommandSender.
     *
     * @param sender CommandSender to send all messages to.
     */
    public void sendTo(CommandSender sender) {
        Validate.notNull(sender, "Sender must not be null.");

        for (FancyMessage message : getFormattedMessages()) {
            message.send(sender);
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

        for (FancyMessage message : getFormattedMessages(replacements)) {
            message.send(sender);
        }
    }

}