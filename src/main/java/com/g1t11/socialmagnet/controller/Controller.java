package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.data.Database;
import com.g1t11.socialmagnet.view.page.PageView;

public abstract class Controller {
    public Navigation nav;

    protected PageView view;

    public Controller(Navigation nav) {
        this.nav = nav;
    }

    public Database database() {
        return nav.database(); 
    }

    public void setStatus(String text) {
        view.setStatus(text);
    }

    /**
     * The associated <code>View</code> is rendered before any input is handled.
     * 
     * @see View
     */
    public void run() {
        updateView();
        if (view != null) view.display();
        handleInput();
    }

    public void updateView() {};

    public abstract void handleInput();
}
