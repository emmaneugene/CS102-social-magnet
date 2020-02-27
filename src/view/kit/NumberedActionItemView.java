package view.kit;

import model.NumberedAction;

public class NumberedActionItemView extends View {
    NumberedAction action;

    public NumberedActionItemView(NumberedAction action) {
        this.action = action;
    }

    @Override
    public void render() {
        System.out.printf("%c. %s\n", action.getTrigger(), action.getLabel());
    }
}
