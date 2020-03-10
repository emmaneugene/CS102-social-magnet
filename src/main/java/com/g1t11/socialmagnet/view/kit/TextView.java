package com.g1t11.socialmagnet.view.kit;

import java.util.Objects;

import com.g1t11.socialmagnet.util.Painter;

public class TextView extends View {
    private String text;

    public TextView(String text, Painter.Color color) {
        this.text = Painter.paint(text, color);
    }

    public TextView(String text) {
        this.text = text;
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

    /**
     * Rendering a text view will print the underlying text to console and
     * place the cursor on a new line.
     */
    @Override
    public void render() {
        System.out.println(text);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TextView)) return false;
        TextView other = (TextView) o;
        return Objects.equals(text, other.text);
    }
}
