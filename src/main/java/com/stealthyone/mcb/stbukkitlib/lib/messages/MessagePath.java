package com.stealthyone.mcb.stbukkitlib.lib.messages;

/**
 * Another way of retrieving messages. Can potentially be useful for enumerators.
 */
public interface MessagePath {

    /**
     * The path of the message that the implementation represents.
     *
     * @return Message path. (Same format as what's described in MessageManager.getMessage(String messagePath))
     */
    public String getPath();

}