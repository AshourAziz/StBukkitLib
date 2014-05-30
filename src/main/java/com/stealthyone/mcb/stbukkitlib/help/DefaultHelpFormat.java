package com.stealthyone.mcb.stbukkitlib.help;

public class DefaultHelpFormat extends HelpFormat {

    public DefaultHelpFormat() {
        super(null, null);

        header = null;
        footer = null;
        title = "&8=====&a{TITLE} &6pg.{PAGE}/{MAXPAGES}&8=====";
        pageNotice = "&cType &6/{LABEL}{COMMAND}{NEXTPAGE} &cfor the next page.";
        itemsPerPage = 8;
    }



}
