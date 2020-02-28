package app;

import java.util.List;
import java.util.ArrayList;

import controller.Controller;

public class App {
    private static App sharedInstance = null;

    private String appName;

    private List<Controller> navigationStack = new ArrayList<>();

    private App() {}

    public static App shared() {
        if (sharedInstance == null)
            sharedInstance = new App();
        return sharedInstance;
    }

    public String getAppName() {
        return appName;
    }

    /**
     * Allows the app name to be set only once
     * @param appName Application name
     */
    public void setAppName(String appName) {
        if (this.appName == null)
            this.appName = appName;
    }

    public void setFirstController(Controller first) {
        navigationStack.add(first);
    }

    public Controller getCurrentController() {
        int size = navigationStack.size();
        if (size == 0) return null;
        return navigationStack.get(size - 1);
    }

    public void run() {
        getCurrentController().run();
    }

    /**
      * Removes the current Controller from the navigation stack and returns 
      * the previous Controller
      *
      * @return Instance of the previous Controller on the navigation stack
      */
    public void navigateBack() {
        int size = navigationStack.size();
        if (size <= 1) return;

        navigationStack.remove(size - 1);

        getCurrentController().run();
    }

    public void navigateTo(Controller next) {
        navigationStack.add(next);

        getCurrentController().run();
    }
}
