package com.stealthyone.mcb.stbukkitlib.messages.mcml;

import mkremins.fanciful.FancyMessage;
import org.apache.commons.lang.Validate;

abstract class MCMLClickEvent {

    public static MCMLClickEvent parseText(MCMLBuilder builder, String rawText) {
        Validate.notNull(rawText, "Raw text cannot be null.");
        if (!rawText.matches("\\[(url|cmd|scmd):(.+)\\]")) {
            throw new IllegalArgumentException("Invalid click event syntax: '" + rawText + "'");
        }

        switch (rawText.substring(1, 4)) {
            case "cmd":
                return new MCMLClickEventCommand(rawText);

            case "url":
                return new MCMLClickEventURL(rawText);

            default:
                return new MCMLClickEventSuggestCommand(rawText);
        }
    }

    public abstract void buildOn(FancyMessage message);

}