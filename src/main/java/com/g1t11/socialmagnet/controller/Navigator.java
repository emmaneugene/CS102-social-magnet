package com.g1t11.socialmagnet.controller;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.data.Database;

public class Navigator {
    private Database db;

    /**
     * Stack of <code>Controller</code> instances that represent the current
     * application's navigation state.
     * <p>
     * On every frame update, the top-most <code>Controller</code> is used to
     * handle UI updates and application logic.
     * @see Controller
     */
    private List<Controller> navigationStack = new ArrayList<>();

    public Navigator(Database db) {
        this.db = db;
    }

    public Database database() {
        return db;
    }

    /**
     * Initiate the navigation stack.
     * @param first The root <code>Controller</code> for navigation.
     * @see Controller
     */
    public void setFirstController(Controller first) {
        navigationStack.clear();
        navigationStack.add(first);
    }

    public Controller currController() {
        int size = navigationStack.size();
        if (size == 0) return null;
        return navigationStack.get(size - 1);
    }

    public void setCurrStatus(String text) {
        currController().setStatus(text);
    }

    /**
     * Remove the current <code>Controller</code> from the navigation stack and
     * prepare the application to navigate to the previous Controller.
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
     * Pop the navigation stack until a Controller of classToFind is found, or
     * until one controller is left on the stack.
     * @param classToFind The class of the type of controller to find.
     * @param <T> The type of class to accept.
     */
    public <T extends Controller> void popTo(Class<T> classToFind) {
        while (navigationStack.size() > 1
                && !currController().getClass().equals(classToFind)) {
            pop();
        }
    }

    public void popToFirst() {
        Controller firstController = navigationStack.get(0);
        navigationStack.clear();
        navigationStack.add(firstController);
    }

    /**
     * Prepare the next <code>Controller</code> to be loaded onto the navigation
     * stack.
     * @param next The <code>Controller</code> of the page to navigate to.
     * @see Controller
     */
    public void push(Controller next) {
        navigationStack.add(next);
    }
}
