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
     * @param formatText The string to be painted. Wrap sections of the string
     * to be painted with {{ and }}.
     * @param colors A sequence of {@link Color} to paint each section positionally.
     * @return A painted string with all original format code untouched.
     */
    public static String paintf(String formatText, Color ...colors) {
        Object[] colorCodes = new String[colors.length * 2];
        for (int i = 0; i < colors.length; i++) {
            colorCodes[i * 2] = map.get(colors[i]);
            colorCodes[i * 2 + 1] = map.get(Color.RESET);
        }

        String noFormatCodes = formatText.replaceAll("%", "%%");
        String preparedForPaint = noFormatCodes.replaceAll("\\{\\{", "%s").replaceAll("\\}\\}", "%s");
        return String.format(preparedForPaint, colorCodes);
    }
}