package com.g1t11.socialmagnet.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.data.Session;

public class Navigation {
    private Session sess;

    /**
     * Stack of <code>Controller</code> instances that represent the current
     * application's navigation state.
     * <p>
     * On every frame update, the top-most <code>Controller</code> is used to 
     * handle UI updates and application logic.
     * 
     * @see Controller
     */
    private List<Controller> navigationStack = new ArrayList<>();

    public Navigation(Connection conn) {
        sess = new Session(conn);
    }

    public Session session() {
        return sess;
    }

    private void initControllerNav(Controller controller) {
        controller.setNavigation(this);
    }

    /**
     * Initiate the navigation stack.
     * 
     * @param first The root <code>Controller</code> for navigation.
     * 
     * @see Controller
     */
    public void setFirstController(Controller first) {
        initControllerNav(first);
        navigationStack.clear();
        navigationStack.add(first);
    }

    public Controller currentController() {
        int size = navigationStack.size();
        if (size == 0) return null;
        return navigationStack.get(size - 1);
    }

    /**
     * Remove the current <code>Controller</code> from the navigation stack and
     * prepare the application to navigate to the previous Controller.
     * 
     * @see Controller
     */
    public void pop() {
        int size = navigationStack.size();
        if (size <= 1) return;

        navigationStack.remove(size - 1);
    }

    public void pop(int count) {
        for (int i = 0; i < count; i++) {
            pop();
        }
    }

    /**
     * Prepare the next <code>Controller</code> to be loaded onto the navigation
     * stack.
     * 
     * @param next The <code>Controller</code> of the page to navigate to.
     * 
     * @see Controller
     */
    public void push(Controller next) {
        initControllerNav(next);
        navigationStack.add(next);
    }
}
