package view.kit;

public class PromptView extends TextView {
    /**
     * @param text The message of the prompt
     * @param promptIndicator The symbol between the prompt message and input
     */
    public PromptView(String text, String promptIndicator) {
        super(String.format("%s %s ", text, promptIndicator));
        this.newLine = false;
    }

    public PromptView(String text) {
        this(text, ">");
    }

    public PromptView() {
        this("Enter your choice");
    }
}
