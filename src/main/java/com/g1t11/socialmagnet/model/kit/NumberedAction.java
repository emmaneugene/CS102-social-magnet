package com.g1t11.socialmagnet.model.kit;

import com.g1t11.socialmagnet.view.kit.ListItemDataSource;

public class NumberedAction extends Action implements ListItemDataSource {
    public NumberedAction(String label, String trigger) {
        super(label, trigger);
    }


    @Override
    public String toString() {
        return String.format("%s. %s\n", getTrigger(), getLabel());
    }

    //============================//
    // ItemViewDataSource methods //
    //============================//
    @Override
    public void render() {
        System.out.printf("%s. %s\n", getTrigger(), getLabel());
    }
}