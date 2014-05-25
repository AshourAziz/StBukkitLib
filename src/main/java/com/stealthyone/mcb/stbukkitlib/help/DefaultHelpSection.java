package com.stealthyone.mcb.stbukkitlib.help;

import org.bukkit.configuration.ConfigurationSection;

public class DefaultHelpSection extends HelpSection {

    protected DefaultHelpSection() {
        super(null, null, null);
    }

    @Override
    protected void load(ConfigurationSection config) {
        footer = header = "&8==========";
        title = "&8Help: &a{TOPIC} &8(pg.{PAGE}/{MAXPAGES})";
        pageNotice = "&8Type /{LABEL}{COMMAND}{NEXTPAGE}";
        itemsPerPage = 8;
    }

}