package com.g1t11.socialmagnet.util;

import java.util.Map;
import java.util.HashMap;

public class Painter {
    public enum Color {
        RESET,
        BOLD,
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
            put(Color.BOLD, "\u001b[1m");
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
     * Paint a string by marking regions of text with <code>[{</code> and 
     * <code>}]</code>.
     * <p>
     * Colors are assigned to nested regions based on the order of opening
     * markers from left to right.
     * 
     * @param formatText The string to be painted. Wrap sections of the string
     * to be painted with [{ and }].
     * @param colors A sequence of {@link Color} to paint each section positionally.
     * @return A painted string with all original format code untouched.
     */
    public static String paintf(String formatText, Color ...colors) {
        int colorIndex = 0;
        int stackIndex = 0;
        
        Color[] stack = new Color[colors.length];
        String result = "";

        String[] tokens = formatText.split("(?=\\[\\{|\\}\\])|(?<=\\[\\{|\\}\\])");
        for (String token : tokens) {
            if (token.equals("[{")) {
                stack[stackIndex++] = colors[colorIndex++];
            } else if (token.equals("}]")) {
                stackIndex--;
                result += map.get(Color.RESET);
            } else {
                result += getMinifiedColorCodes(stack, stackIndex);
                result += token;
            }
        }
        return result;
    }

    /**
     * The minified codes that represent the current stack of colors.
     * <p>
     * Chromatic colors will overwrite other chromatic colors below it on the
     * stack, but Color.BOLD will overlap as long as it exists on the stack.
     */
    private static String getMinifiedColorCodes(Color[] stack, int length) {
        boolean colored = false;
        boolean bolded = false;
        String result = "";
        for (int i = length - 1; i >= 0; i--) {
            if (!bolded && stack[i] == Color.BOLD) {
                result += map.get(Color.BOLD);
                bolded = true;
            } else if (!colored) {
                result += map.get(stack[i]);
                colored = true;
            }
        }
        return result;
    }
}