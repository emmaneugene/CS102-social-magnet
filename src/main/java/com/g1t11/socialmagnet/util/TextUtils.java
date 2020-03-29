package com.g1t11.socialmagnet.util;

public class TextUtils {
    public static String countedWord(int count, String singular, String plural) {
        if (count == 1) return String.format("%d %s", count, singular);
        return String.format("%d %s", count, plural);
    }

    public static String prettyNumber(int number) {
        if (number < 1000) return Integer.toString(number);
        StringBuilder sb = new StringBuilder();
        while (number >= 1000) {
            sb.insert(0, number % 1000);
            sb.insert(0, ",");
            number /= 1000;
        }
        sb.insert(0, number);
        return sb.toString();
    }
}
