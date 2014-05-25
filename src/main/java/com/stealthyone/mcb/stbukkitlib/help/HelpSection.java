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

    protected String header;
    protected String footer;
    protected String title;
    protected String pageNotice;
    protected int itemsPerPage;

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
        options = new HelpOptions(this, config);

        /* Load format */
        ConfigurationSection formatSec = config.getConfigurationSection("format");
        if (formatSec == null) {
            formatSec = config.createSection("format");
        }
        header = formatSec.getString("header");
        footer = formatSec.getString("footer");
        title = formatSec.getString("title");
        pageNotice = formatSec.getString("pageNotice");
        itemsPerPage = formatSec.getInt("itemsPerPage", -1);

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

    public String getHeader() {
        return header != null ? header : parent.getHeader();
    }

    public String getFooter() {
        return footer != null ? footer : parent.getFooter();
    }

    public String getTitle() {
        return title != null ? title : parent.getTitle();
    }

    public String getPageNotice() {
        return pageNotice != null ? pageNotice : parent.getPageNotice();
    }

    public int getItemsPerPage() {
        return itemsPerPage != -1 ? itemsPerPage : parent.getItemsPerPage();
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