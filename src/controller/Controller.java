package controller;

import view.kit.View;

public abstract class Controller {
    /**
     * All {@link Controller} objects have an associated {@link View}
     * @see View
     */
    protected View view;

    /**
     * The associated {@link View} is rendered before any input is handled.
     */
    public void run() {
        updateView();
        handleInput();
    }

    public abstract void updateView();

    public abstract void handleInput();
}
