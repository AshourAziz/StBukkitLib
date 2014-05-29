package com.stealthyone.mcb.stbukkitlib.messages;

import java.util.List;

public class DummyMessage extends Message {

    public DummyMessage(MessageManager messageManager, String message) {
        super(messageManager, null, null);
        getMessages().add(message);
    }

    public DummyMessage(MessageManager messageManager, List<String> messages) {
        super(messageManager, null, null);
        getMessages().addAll(messages);
    }

}