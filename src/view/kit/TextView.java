package view.kit;

public class TextView extends View {
    private String text;

    boolean newLine = true;

    public TextView(String text) {
        this.text = text;
    }

    public TextView(String text, boolean newLine) {
        this(text);
        this.newLine = newLine;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void renderMain() {
        if (newLine) {
            System.out.println(text);
        } else {
            System.out.print(text);
        }
    }
}
