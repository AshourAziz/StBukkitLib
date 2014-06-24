package com.stealthyone.mcb.stbukkitlib.messages.mcml;

import mkremins.fanciful.FancyMessage;
import org.apache.commons.lang.Validate;

class MCMLClickEventURL extends MCMLClickEvent {

    private String url;

    MCMLClickEventURL(String rawText) {
        Validate.notNull(rawText, "Raw text cannot be null.");
        if (rawText.length() == 6) {
            throw new IllegalArgumentException("URL click event must have input.");
        }
        url = rawText.substring(5, rawText.length());
    }

    @Override
    public void buildOn(FancyMessage message) {
        message.link(url);
    }

}