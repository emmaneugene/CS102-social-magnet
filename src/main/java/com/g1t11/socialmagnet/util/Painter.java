package com.g1t11.socialmagnet.util;

import java.util.Map;
import java.util.HashMap;

public class Painter {
    public enum Color {
        RESET,
        BLACK,
        RED,
        GREEN,
        YELLOW,
        BLUE,
        PURPLE,
        CYAN,
        WHITE
    }

    protected static final Map<Color, String> map = new HashMap<Color, String>() {
        private static final long serialVersionUID = 1L;
        {
            put(Color.RESET, "\u001b[0m");
            put(Color.BLACK, "\u001b[30m");
            put(Color.RED, "\u001b[31m");
            put(Color.GREEN, "\u001b[32m");
            put(Color.YELLOW, "\u001b[33m");
            put(Color.BLUE, "\u001b[34m");
            put(Color.PURPLE, "\u001b[35m");
            put(Color.CYAN, "\u001b[36m");
            put(Color.WHITE, "\u001b[37m");
        }
    };

    public static String paint(String text, Color color) {
        return map.get(color) + text + map.get(Color.RESET);
    }

    /**
     * Paint a string by placing color markers within the format.
     * 
     * @param formatText The string to be painted. Mark where color codes
     * should be placed by placing <code>%r</code> format markers.
     * @param colors A sequence of {@link Color} to be substituted positionally
     * with the <code>%r</code> markers.
     * @return A painted string with all original format code untouched.
     */
    public static String paintf(String formatText, Color ...colors) {
        Object[] colorCodes = new String[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colorCodes[i] = map.get(colors[i]);
        }

        String noFormatCodes = formatText.replaceAll("%", "%%");
        String preparedForPaint = noFormatCodes.replaceAll("%%r", "%s");
        return String.format(preparedForPaint, colorCodes) + map.get(Color.RESET);
    }
}