package com.g1t11.socialmagnet.util;

import java.util.List;

/**
 * This class consists exclusively of static methods that generate the
 * appropriate string accordingly to the counts of words/numbers/lists.
 */
public class TextUtils {
    /**
     * This method is used to check if the input string is alphanumeric.
     * @param input The input string used for validation.
     * @return If input is alphanumeric, it will return true, else return false.
     */
    public static boolean isAlphanumeric(String input) {
        return input != null && input.matches("^[a-zA-Z0-9]+$");
    }

    /**
     * Formats a word according to a quantity.
     * @param count The quantity to format against.
     * @param singular The singular form of the word.
     * @param plural The plural form of the word.
     * @return A string in the format of "0 foos", "1 bar", "2 bazzes".
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
     * (e.g 1,000,000)
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
     * Generate a pretty representation of an array with comma delimiters, the
     * Oxford comma, and the use of "and" before the last item.
     * <p>
     * For example, <code>{"adam", "sally"}</code> will produce <code>"adam and
     * sally"</code>, while <code>{"adam", "sally", "john"}</code> will produce
     * <code>"adam, sally, and john"</code>.
     * @param <T> The type of the items in the array.
     * @param items The input array of items.
     * @return A pretty representation of the array.
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
     * Generate a pretty representation of a list with comma delimiters, the
     * Oxford comma, and the use of "and" before the last item.
     * <p>
     * For example, <code>{"adam", "sally"}</code> will produce <code>"adam and
     * sally"</code>, while <code>{"adam", "sally", "john"}</code> will produce
     * <code>"adam, sally, and john"</code>.
     * @param <T> The type of the items in the list.
     * @param items The input list of items.
     * @return A pretty representation of the list.
     * @see #prettyList(Object[])
     */
    public static <T> String prettyList(List<? extends T> items) {
        return prettyList(items.toArray());
    }
}
