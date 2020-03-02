package com.g1t11.socialmagnet.controller;

import java.util.ArrayList;
import java.util.List;

public class Navigation {

    /**
     * Stack of {@link Controller} instances that represent the current
     * application's navigation state.
     * On every frame update, the top-most <code>Controller</code> is used
     * to handle UI updates and application logic.
     */
    private List<Controller> navigationStack = new ArrayList<>();

    /**
     * Initiate the navigation stack.
     * @param first The root {@link Controller} for navigation.
     */
    public void setFirstController(Controller first) {
        if (navigationStack.isEmpty())
            navigationStack.add(first);
    }

    public Controller getCurrentController() {
        int size = navigationStack.size();
        if (size == 0) return null;
        return navigationStack.get(size - 1);
    }

    /**
     * Remove the current {@link Controller} from the navigation stack and
     * prepare the application to navigate to the previous
     * <code>Controller</code>.
     */
    public void pop() {
        int size = navigationStack.size();
        if (size <= 1) return;

        navigationStack.remove(size - 1);
    }

    /**
     * Prepare the next {@link Controller} to be loaded onto the navigation
     * stack.
     * @param next The <code>Controller</code> of the page to navigate to.
     */
    public void prepareForNavigation(Controller next) {
        navigationStack.add(next);
    }

    public void runCurrentController() {
        getCurrentController().run();
    }
}
