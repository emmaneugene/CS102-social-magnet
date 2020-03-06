package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.view.kit.*;

public abstract class Controller {

    protected Navigation nav;

    protected PageView view;

    /**
     * The associated {@link View} is rendered before any input is handled.
     */
    public void run() {
        updateView();
        handleInput();
    }

    public abstract void updateView();

    public abstract void handleInput();
    /**
     * All {@link Controller} objects have an associated {@link PageView}
     * 
     * @see PageView
     */
    public PageView getView() {
        return view;
    };

    public void setNavigation(Navigation nav) {
        this.nav = nav;
    }
}
