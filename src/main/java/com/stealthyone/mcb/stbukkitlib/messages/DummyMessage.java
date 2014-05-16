package com.stealthyone.mcb.stbukkitlib.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMessage extends Message {

    private List<String> messages;

    public DummyMessage(String message) {
        super(null, null, null);
        messages = new ArrayList<>(Arrays.asList(message));
    }

    public DummyMessage(List<String> messages) {
        super(null, null, null);
        this.messages = messages;
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }

}