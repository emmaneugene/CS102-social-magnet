package view.kit;

public class PromptView extends View {
    private String promptMessage;

    public PromptView(String promptMessage) {
        this.promptMessage = promptMessage;
    }

    public PromptView() {
        this.promptMessage = "Enter your choice";
    }

	@Override
	public void renderMain() {
		System.out.printf("%s > ", promptMessage);
	}
}
