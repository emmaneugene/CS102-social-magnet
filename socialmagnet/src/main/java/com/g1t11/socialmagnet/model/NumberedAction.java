package com.g1t11.socialmagnet.model;

import com.g1t11.socialmagnet.view.kit.ListItemDataSource;

public class NumberedAction extends Action implements ListItemDataSource {
    public NumberedAction(String label, String trigger) {
        super(label, trigger);
    }


    //============================//
    // ItemViewDataSource methods //
    //============================//
	@Override
	public void render() {
        System.out.printf("%s. %s\n", getTrigger(), getLabel());
	}
}
