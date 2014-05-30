package com.stealthyone.mcb.stbukkitlib.help;

import org.bukkit.configuration.ConfigurationSection;

public class HelpFormat {

    private HelpSection helpSection;

    protected String header;
    protected String footer;
    protected String title;
    protected String pageNotice;
    protected int itemsPerPage;

    public HelpFormat(HelpSection helpSection, ConfigurationSection config) {
        this.helpSection = helpSection;

        ConfigurationSection formatSec = config.getConfigurationSection("format");
        if (formatSec != null) {
            header = formatSec.getString("header");
            footer = formatSec.getString("footer");
            title = formatSec.getString("title");
            pageNotice = formatSec.getString("pageNotice");
            itemsPerPage = formatSec.getInt("itemsPerPage", -1);
        }
    }

    public String getHeader() {
        return header;
    }

    public String getFooter() {
        return footer;
    }

    public String getTitle() {
        return title;
    }

    public String getPageNotice() {
        return pageNotice;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

}