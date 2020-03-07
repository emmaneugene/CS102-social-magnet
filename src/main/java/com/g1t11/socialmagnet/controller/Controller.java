package com.g1t11.socialmagnet.controller;

import java.sql.Connection;

import com.g1t11.socialmagnet.view.kit.*;

public abstract class Controller {
    protected Navigation nav;

    protected PageView view;

    protected Connection conn;

    public Controller(Connection conn) {
        this.conn = conn;
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

    public abstract void updateView();

    public abstract void handleInput();
    /**
     * All <code>Controller</code> objects have an associated <code>PageView</code>.
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
