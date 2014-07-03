package com.stealthyone.mcb.stbukkitlib.messages;

public class AdvancedDummyMessage extends AdvancedMessage {

    AdvancedDummyMessage(String message) {
        super(null, null, null);
        getMessages().add(message);
    }

}