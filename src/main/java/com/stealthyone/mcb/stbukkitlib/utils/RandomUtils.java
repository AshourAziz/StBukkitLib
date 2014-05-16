package com.stealthyone.mcb.stbukkitlib.utils;

import java.util.Random;

public final class RandomUtils {

    private RandomUtils() { }

    /**
     * Returns a random string.
     *
     * @param length Length of string to generate.
     * @param includeNumbers True to include numbers.
     *                       False to only use letters.
     * @return Randomly generated string.
     */
    public static String getRandomString(int length, boolean includeNumbers) {
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] numbers = "1234567890".toCharArray();

        Random random = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            if (includeNumbers && random.nextBoolean()) {
                text[i] = numbers[random.nextInt(numbers.length)];
            } else {
                text[i] = chars[random.nextInt(chars.length)];
            }
        }
        return new String(text);
    }

}