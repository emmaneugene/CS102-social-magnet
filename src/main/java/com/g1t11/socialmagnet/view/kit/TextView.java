package com.g1t11.socialmagnet.view.kit;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

public class TextView implements View {
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
            put(Color.RESET, "\u001B[0m");
            put(Color.BLACK, "\u001B[30m");
            put(Color.RED, "\u001B[31m");
            put(Color.GREEN, "\u001B[32m");
            put(Color.YELLOW, "\u001B[33m");
            put(Color.BLUE, "\u001B[34m");
            put(Color.PURPLE, "\u001B[35m");
            put(Color.CYAN, "\u001B[36m");
            put(Color.WHITE, "\u001B[37m");
        }
    };

    private String text;

    public TextView(String text, boolean newLine, Color color) {
        this.text = String.format("%s%s%s", map.get(color), text, map.get(Color.RESET));
    }

    public TextView(String text, boolean newLine) {
        this(text, newLine, Color.RESET);
    }

    public TextView(String text, Color color) {
        this(text, true, color);
    }

    public TextView(String text) {
        this(text, true);
    }

    public TextView() {
        this("");
    }

    public TextView(String formatText, Color ...colors) {
        Object[] colorCodes = new String[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colorCodes[i] = map.get(colors[i]);
        }
        this.text = String.format(formatText, colorCodes);
    }

    public String getText() {
        return text;
    }

    public void setText(String text, Color color) {
        this.text = String.format("%s%s%s", map.get(color), text, map.get(Color.RESET));
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void render() {
        System.out.print(text + "\n");
    }

    /**
     * Returns the internal text without a line break.
     */
    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TextView)) return false;
        TextView other = (TextView) o;
        return Objects.equals(text, other.text);
    }
}
