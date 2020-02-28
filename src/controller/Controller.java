package controller;

import view.kit.View;

public abstract class Controller {
    protected View view;

    public void run() {
        updateView();
        getInput();
    }

    public abstract void updateView();

    public abstract void getInput();
}
