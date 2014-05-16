package com.stealthyone.mcb.stbukkitlib.utils;

public final class MiscUtils {

    private MiscUtils() { }

    public static int getPageCount(int itemCount, int amtPerPage) {
        int pageCount = 0;
        for (int i = 1; i <= itemCount; i++) {
            if (i % amtPerPage == 1) pageCount++;
        }
        return pageCount;
    }

}