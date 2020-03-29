package com.g1t11.socialmagnet.util;

public class Painter {
    public enum Color {
        RESET("\u001b[0m"),
        BOLD("\u001b[1m"),
        BLACK("\u001b[30m"),
        RED("\u001b[31m"),
        GREEN("\u001b[32m"),
        YELLOW("\u001b[33m"),
        BLUE("\u001b[34m"),
        PURPLE("\u001b[35m"),
        CYAN("\u001b[36m"),
        WHITE("\u001b[37m");

        public String code;

        private Color(String code) {
            this.code = code;
        }
    }

    public static String paint(String text, Color color) {
        return color.code + text + Color.RESET.code;
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
        
        String[] tokens = formatText.split("(?=\\[\\{|\\}\\])|(?<=\\[\\{|\\}\\])");

        Color[] stack = new Color[tokens.length];
        String result = "";
        for (String token : tokens) {
            if (token.equals("[{")) {
                stack[stackIndex++] = colors[Math.min(colorIndex++, colors.length - 1)];
            } else if (token.equals("}]")) {
                stackIndex--;
                result += Color.RESET.code;
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
     * <p>
     * If fewer colors are provided than there are regions, the last color will
     * be used to paint the remaining regions.
     */
    private static String getMinifiedColorCodes(Color[] stack, int length) {
        boolean colored = false;
        boolean bolded = false;
        String result = "";
        for (int i = length - 1; i >= 0; i--) {
            if (!bolded && stack[i] == Color.BOLD) {
                result += Color.BOLD.code;
                bolded = true;
            } else if (!colored) {
                result += stack[i].code;
                colored = true;
            }
        }
        return result;
    }
}
