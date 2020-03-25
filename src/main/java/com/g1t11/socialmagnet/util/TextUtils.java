package com.g1t11.socialmagnet.util;

public class TextUtils {
    public static String countedWord(int count, String singular, String plural) {
        if (count == 1) return String.format("%d %s", count, singular);
        return String.format("%d %s", count, plural);
    }
}