package com.g1t11.socialmagnet.view.kit;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

public class TextView extends View {
    public enum Color {
        NONE,
        BLACK,
        RED,
        GREEN,
        YELLOW,
        BLUE,
        PURPLE,
        CYAN
    }

    protected static final Map<Color, String> map = new HashMap<Color, String>() {
        private static final long serialVersionUID = 1L;
        {
            put(Color.NONE, "\u001B[0m");
            put(Color.BLACK, "\u001B[30m");
            put(Color.RED, "\u001b[31m");
            put(Color.GREEN, "\u001b[32m");
            put(Color.YELLOW, "\u001b[33m");
            put(Color.BLUE, "\u001b[34m");
            put(Color.PURPLE, "\u001b[35m");
            put(Color.CYAN, "\u001b[36m");
        }
    };

    private String text;

    boolean newLine;

    public TextView(String text, boolean newLine, Color color) {
        this.text = String.format("%s%s%s", map.get(color), text, map.get(Color.NONE));
        this.newLine = newLine;
    }

    public TextView(String text, boolean newLine) {
        this(text, newLine, Color.NONE);
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

    public String getText() {
        return text;
    }

    public void setText(String text, Color color) {
        this.text = String.format("%s%s%s", map.get(color), text, map.get(Color.NONE));
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void render() {
        if (newLine) {
            System.out.println(text);
        } else {
            System.out.print(text);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TextView)) return false;
        TextView other = (TextView) o;
        return Objects.equals(text, other.text)
            && Objects.equals(newLine, other.newLine);
    }
}
