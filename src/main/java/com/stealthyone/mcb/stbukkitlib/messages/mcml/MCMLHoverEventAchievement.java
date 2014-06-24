package com.stealthyone.mcb.stbukkitlib.messages.mcml;

import mkremins.fanciful.FancyMessage;
import org.apache.commons.lang.Validate;

class MCMLHoverEventAchievement extends MCMLHoverEvent {

    private String achievement;

    MCMLHoverEventAchievement(String rawText) {
        Validate.notNull(rawText, "Raw text cannot be null.");
        if (rawText.length() == 6) {
            throw new IllegalArgumentException("Achievement hover event must have input.");
        }
        achievement = rawText.substring(5, rawText.length() - 1);
    }

    @Override
    public void buildOn(FancyMessage message) {
        message.achievementTooltip(achievement);
    }

}