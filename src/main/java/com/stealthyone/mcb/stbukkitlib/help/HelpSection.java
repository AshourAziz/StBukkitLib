package com.stealthyone.mcb.stbukkitlib.help;

import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class HelpSection {

    private HelpManager helpManager;
    private HelpSection parent;
    private String name;
    private String path;
    private Map<String, HelpSection> children = new LinkedHashMap<>();

    private HelpOptions options;
    protected HelpFormat format;

    private List<String> messages;

    protected HelpSection(HelpManager helpManager, HelpSection parent, ConfigurationSection config) {
        this.helpManager = helpManager;
        this.parent = parent;
        name = config == null ? "" : config.getName();
        if (parent == null) {
            path = name;
        } else {
            this.path = parent.getPath() != null && !parent.getPath().equals("") ? (parent.getPath() + (name != null && !name.equals("") ? (" " + name) : name)) : name;
        }
        load(config);
    }

    protected void load(ConfigurationSection config) {
        if (this instanceof DefaultHelpSection) {
            format = new DefaultHelpFormat();
            return;
        }
        format = new HelpFormat(this, config);
        options = new HelpOptions(this, config);

        /* Load messages */
        messages = new ArrayList<>(config.getStringList("messages"));

        /* Load children */
        ConfigurationSection childSec = config.getConfigurationSection("children");
        if (childSec != null) {
            for (String name : childSec.getKeys(false)) {
                HelpSection helpSection = new HelpSection(helpManager, this, childSec.getConfigurationSection(name));
                children.put(name.toLowerCase(), helpSection);
            }
        }
    }

    public HelpSection getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public HelpOptions getOptions() {
        return options;
    }

    public List<String> getMessages() {
        return messages;
    }

    public HelpSection getChild(String name) {
        return children.get(name.toLowerCase());
    }

    public Collection<HelpSection> getChildren() {
        return children.values();
    }

}