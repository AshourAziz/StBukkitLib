package com.stealthyone.mcb.stbukkitlib.messages.mcml;

import mkremins.fanciful.FancyMessage;
import org.apache.commons.lang.Validate;
import org.bukkit.inventory.ItemStack;

class MCMLHoverEventItem extends MCMLHoverEvent {

    private ItemStack itemStack;

    MCMLHoverEventItem(MCMLBuilder builder, String rawText) {
        Validate.notNull(rawText, "Raw text cannot be null.");
        if (rawText.length() == 6) {
            throw new IllegalArgumentException("Item hover event must have input.");
        }
        String key = rawText.substring(5, rawText.length() - 1);
        Object item = builder.getReplacement(key);
        if (item == null || !(item instanceof ItemStack)) {
            throw new IllegalArgumentException("No replacement item found with key '" + key + "'");
        }
        itemStack = (ItemStack) item;
    }

    @Override
    public void buildOn(FancyMessage message) {
        message.itemTooltip(itemStack);
    }

}