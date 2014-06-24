package com.stealthyone.mcb.stbukkitlib.messages.mcml;

import mkremins.fanciful.FancyMessage;
import org.apache.commons.lang.Validate;

abstract class MCMLHoverEvent {

    public static MCMLHoverEvent parseText(MCMLBuilder builder, String rawText) {
        Validate.notNull(rawText, "Raw text cannot be null.");
        if (!rawText.matches("\\[(ach|itm|txt):(.+)\\]")) {
            throw new IllegalArgumentException("Invalid hover event syntax: '" + rawText + "'");
        }

        switch (rawText.substring(1, 4)) {
            case "ach":
                return new MCMLHoverEventAchievement(rawText);

            case "itm":
                return new MCMLHoverEventItem(builder, rawText);

            case "txt":
                return new MCMLHoverEventText(rawText);
        }
        return null;
    }

    public abstract void buildOn(FancyMessage message);

}