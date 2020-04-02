package com.g1t11.socialmagnet.util;

import java.util.List;

/**
 * This class consists exclusively of static methods that generate the
 * appropriate string accordingly to the counts of words/numbers/lists.
 */
public class TextUtils {

    /**
     * Build appropriate string whether if it is singular or plural.
     * @param count The number of words.
     * @param singular The string to use if it is singular.
     * @param plural The string to use if it is plural.
     * @return Generated appropriate string.
     */
    public static String countedWord(int count,
            String singular, String plural) {
        if (count == 1) {
            return String.format("%d %s", count, singular);
        }
        return String.format("%d %s", count, plural);
    }

    /**
     * Build appropriate string with ',' according to the input number.
     * @param number The input number
     * @return Generated appropriate string wuth ','.
     */
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

    /**
     * Build approriate string according to array of items.
     * @param <T> Input of generatic type.
     * @param items The input array of items.
     * @return Generated appropriate string.
     */
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

    /**
     * Build approriate string according to list of items.
     * @param <T> Input of List.
     * @param items The input list of items.
     * @return Generated appropriate string.
     */
    public static <T> String prettyList(List<? extends T> items) {
        return prettyList(items.toArray());
    }
}
