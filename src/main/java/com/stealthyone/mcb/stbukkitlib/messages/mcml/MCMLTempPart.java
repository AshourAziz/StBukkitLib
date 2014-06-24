package com.stealthyone.mcb.stbukkitlib.messages.mcml;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

final class MCMLTempPart {

    List<Character> chars = new ArrayList<>();
    ChatColor color;
    boolean isBold = false;
    boolean isItalic = false;
    boolean isUnderline = false;
    boolean isStrikethrough = false;
    boolean isMagic = false;
    MCMLClickEvent clickEvent;
    MCMLHoverEvent hoverEvent;

    public String getText() {
        StringBuilder sb = new StringBuilder();
        for (Character character : chars) {
            sb.append(character);
        }
        return sb.length() == 0 ? null : sb.toString();
    }

}