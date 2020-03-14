package com.g1t11.socialmagnet.view.component;

import java.util.Objects;

public class TextComponent implements Component {
    private String text;

    public TextComponent(String text) {
        this.text = text;
    }

    public TextComponent() {
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
        if (!(o instanceof TextComponent)) return false;
        TextComponent other = (TextComponent) o;
        return Objects.equals(text, other.text);
    }
}
