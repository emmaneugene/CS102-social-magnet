package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.data.Database;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.PageView;

public abstract class Controller {
    public Navigator nav;

    private PageView view;

    protected PromptInput input = new PromptInput();

    public Controller(Navigator nav) {
        this.nav = nav;
    }

    public final Database database() {
        return nav.database(); 
    }

    public PageView getView() {
        return view;
    }

    public void setView(PageView view) {
        this.view = view;
    }

    /**
     * The associated <code>View</code> is rendered before any input is handled.
     * @see View
     */
    public void run() {
        updateView();
        if (view != null) view.display();
        handleInput();
    }

    /**
     * Sets the status of the corresponding {@link PageView}.
     * @param text The status message to set.
     */
    public void setStatus(String text) {
        view.setStatus(text);
    }

    /**
     * Appends a status to the status of the corresponding {@link PageView}.
     * @param text The status message to append.
     */
    public void appendStatus(String text) {
        view.appendStatus(text);
    }

    public void updateView() {};

    public abstract void handleInput();
}
