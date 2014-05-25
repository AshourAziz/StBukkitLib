package com.stealthyone.mcb.stbukkitlib.help;

import org.bukkit.configuration.ConfigurationSection;

public class HelpOptions {

    private HelpSection helpSection;

    private String description;
    private String permission;
    private String permMessage;
    private boolean inherit;

    public HelpOptions(HelpSection helpSection, ConfigurationSection config) {
        this.helpSection = helpSection;

        ConfigurationSection optionSec = config.getConfigurationSection("options");
        if (optionSec != null) {
            description = optionSec.getString("description");
            permission = optionSec.getString("permission");
            permMessage = optionSec.getString("permissionMessage");
            inherit = optionSec.getBoolean("inherit", true);
        }
    }

    public String getDescription() {
        return description;
    }

    public String getPermission() {
        return permission != null ? permission : (!inherit ? null : helpSection.getParent().getOptions().getPermission());
    }

    public String getPermMessage() {
        return permMessage != null ? permMessage : (!inherit ? null : helpSection.getParent().getOptions().getPermMessage());
    }

    public boolean doInherit() {
        return inherit;
    }

}