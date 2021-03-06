package com.stealthyone.mcb.stbukkitlib.utils;

public final class MiscUtils {

    private MiscUtils() { }

    public static int getPageCount(int itemCount, int amtPerPage) {
        if (itemCount == 0 || amtPerPage == 0) {
            return 0;
        }

        int pageCount = 0;
        for (int i = 1; i <= itemCount; i++) {
            if (i % amtPerPage == 1) pageCount++;
        }
        return pageCount;
    }

}