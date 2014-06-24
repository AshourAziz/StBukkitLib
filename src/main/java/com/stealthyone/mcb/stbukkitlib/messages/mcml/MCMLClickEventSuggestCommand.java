package com.stealthyone.mcb.stbukkitlib.messages.mcml;

import mkremins.fanciful.FancyMessage;
import org.apache.commons.lang.Validate;

class MCMLClickEventSuggestCommand extends MCMLClickEvent {

    private String command;

    MCMLClickEventSuggestCommand(String rawText) {
        Validate.notNull(rawText, "Raw text cannot be null.");
        if (rawText.length() == 7) {
            throw new IllegalArgumentException("Suggest command click event must have input.");
        }
        command = rawText.substring(6, rawText.length() - 1);
    }

    @Override
    public void buildOn(FancyMessage message) {
        message.suggest(command);
    }

}