package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.view.kit.*;

public abstract class Controller {
    /**
     * All {@link Controller} objects have an associated {@link View}
     * @see View
     */
    private PageView view;

    /**
     * The associated {@link View} is rendered before any input is handled.
     */
    public void run() {
        updateView();
        handleInput();
    }

    public abstract void updateView();

    public abstract void handleInput();

    public PageView getView() {
        return view;
    }

    @Override
    public String toString() {
        if (view != null)
            return view.toString();
        return super.toString();
    }
}
