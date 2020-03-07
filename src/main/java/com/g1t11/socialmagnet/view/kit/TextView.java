package com.g1t11.socialmagnet.view.kit;

import java.util.Objects;

public class TextView extends View {
    private String text;

    boolean newLine;

    public TextView(String text, boolean newLine) {
        this.text = text;
        this.newLine = newLine;
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
