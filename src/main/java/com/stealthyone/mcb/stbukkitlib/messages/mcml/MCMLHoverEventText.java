package com.stealthyone.mcb.stbukkitlib.messages.mcml;

import mkremins.fanciful.FancyMessage;
import org.apache.commons.lang.Validate;

class MCMLHoverEventText extends MCMLHoverEvent {

    private String text;

    MCMLHoverEventText(String rawText) {
        Validate.notNull(rawText, "Raw text cannot be null.");
        if (rawText.length() == 6) {
            throw new IllegalArgumentException("Text hover event must have input.");
        }
        text = rawText.substring(5, rawText.length());
    }

    @Override
    public void buildOn(FancyMessage message) {
        message.formattedTooltip(new MCMLBuilder(text).buildFancyMessage());
    }

}