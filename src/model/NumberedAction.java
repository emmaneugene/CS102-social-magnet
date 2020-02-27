package model;

import view.kit.ItemViewDataSource;

public class NumberedAction extends Action implements ItemViewDataSource {
    public NumberedAction(String label, char trigger) {
        super(label, trigger);
    }


    // ItemViewDataSource methods
	@Override
	public void render() {
        System.out.printf("%c. %s\n", getTrigger(), getLabel());
	}
}
