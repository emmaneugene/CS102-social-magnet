package model;

import view.kit.ItemViewDataSource;

public class NumberedAction extends Action implements ItemViewDataSource {
    public NumberedAction(String label, String trigger) {
        super(label, trigger);
    }


    // ItemViewDataSource methods
	@Override
	public void render() {
        System.out.printf("%s. %s\n", getTrigger(), getLabel());
	}
}
