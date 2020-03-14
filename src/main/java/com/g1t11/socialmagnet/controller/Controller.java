package com.g1t11.socialmagnet.controller;

import java.sql.Connection;

import com.g1t11.socialmagnet.view.page.PageView;

public abstract class Controller {
    protected Navigation nav;

    protected Connection conn;

    protected PageView view;

    public Controller(Navigation nav) {
        this.nav = nav;
    }

    public void setNavigation(Navigation nav) {
        this.nav = nav;
    }

    public Connection connection() {
        return nav.connection();
    }

    /**
     * The associated <code>View</code> is rendered before any input is handled.
     * 
     * @see View
     */
    public void run() {
        updateView();
        handleInput();
    }

    public void updateView() {
        view.display();
    }

    public abstract void handleInput();

}
