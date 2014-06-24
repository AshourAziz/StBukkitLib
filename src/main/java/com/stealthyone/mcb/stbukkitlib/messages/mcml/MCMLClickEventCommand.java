package com.stealthyone.mcb.stbukkitlib.messages.mcml;

import mkremins.fanciful.FancyMessage;
import org.apache.commons.lang.Validate;

class MCMLClickEventCommand extends MCMLClickEvent {

    private String command;

    MCMLClickEventCommand(String rawText) {
        Validate.notNull(rawText, "Raw text cannot be null.");
        if (rawText.length() == 6) {
            throw new IllegalArgumentException("Command click event must have input.");
        }
        command = rawText.substring(5, rawText.length());
    }

    @Override
    public void buildOn(FancyMessage message) {
        message.command(command);
    }

}