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
        if (isDefaultSec()) {
            return;
        }

        ConfigurationSection formatSec = config.getConfigurationSection("format");
        if (formatSec != null) {
            header = formatSec.getString("header");
            footer = formatSec.getString("footer");
            title = formatSec.getString("title");
            pageNotice = formatSec.getString("pageNotice");
            itemsPerPage = formatSec.getInt("itemsPerPage", -1);
        }
    }

    private boolean isDefaultSec() {
        return this instanceof DefaultHelpFormat;
    }

    public String getHeader() {
        String rheader = this.header;
        if (!isDefaultSec()) {
            rheader = helpSection.getParent().format.getHeader();
        }
        return rheader;
    }

    public String getFooter() {
        String rfooter = this.footer;
        if (!isDefaultSec()) {
            rfooter = helpSection.getParent().format.getFooter();
        }
        return rfooter;
    }

    public String getTitle() {
        String rtitle = this.title;
        if (!isDefaultSec()) {
            rtitle = helpSection.getParent().format.getTitle();
        }
        return rtitle;
    }

    public String getPageNotice() {
        String rpageNotice = this.pageNotice;
        if (!isDefaultSec()) {
            rpageNotice = helpSection.getParent().format.getPageNotice();
        }
        return rpageNotice;
    }

    public int getItemsPerPage() {
        int ritems = this.itemsPerPage;
        if (!isDefaultSec()) {
            ritems = helpSection.getParent().format.getItemsPerPage();
        }
        return ritems;
    }

}