package com.g1t11.socialmagnetservice.util;

import java.util.List;

public class TextUtils {
    public static boolean isAlphanumeric(String input) {
        return input != null && input.matches("^[a-zA-Z0-9]+$");
    }

    public static String countedWord(int count,
            String singular, String plural) {
        if (count == 1) {
            return String.format("%d %s", count, singular);
        }
        return String.format("%d %s", count, plural);
    }

    public static String prettyNumber(int number) {
        if (number < 0) return "-" + prettyNumber(-number);
        if (number < 1000) return Integer.toString(number);
        StringBuilder sb = new StringBuilder();
        while (number >= 1000) {
            sb.insert(0, String.format("%03d", number % 1000));
            sb.insert(0, ",");
            number /= 1000;
        }
        sb.insert(0, number);
        return sb.toString();
    }

    public static <T> String prettyList(T[] items) {
        if (items == null) return null;
        int length = items.length;
        if (length == 0) {
            return "";
        }
        if (length == 1) {
            return items[0].toString();
        }
        if (length == 2) {
            return items[0].toString() + " and " + items[1].toString();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length - 1; i++) {
            sb.append(items[i].toString()).append(", ");
        }
        sb.append("and ").append(items[length - 1]);
        return sb.toString();
    }

    public static <T> String prettyList(List<? extends T> items) {
        return prettyList(items.toArray());
    }
}
