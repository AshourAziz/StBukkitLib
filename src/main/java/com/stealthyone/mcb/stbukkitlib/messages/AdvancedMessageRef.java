package com.stealthyone.mcb.stbukkitlib.messages;

import org.bukkit.command.CommandSender;

import java.util.Map;

public class AdvancedMessageRef implements MessagePath {

    private AdvancedMessageManager messageManager;
    private String path;

    public AdvancedMessageRef(AdvancedMessageManager messageManager, String category, String message) {
        this.messageManager = messageManager;
        this.path = category + "." + message;
    }

    @Override
    public String getPath() {
        return path;
    }

    public void sendTo(CommandSender sender) {
        messageManager.getMessage(this).sendTo(sender);
    }

    public void sendTo(CommandSender sender, Map<String, String> replacements) {
        messageManager.getMessage(this).sendTo(sender, replacements);
    }

}