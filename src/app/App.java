package app;

import java.util.List;
import java.util.ArrayList;

import controller.Controller;

public class App {
    /**
     * Singleton instance representing the current running application.
     * Stores all state variables that are global to the application.
     */
    private static App sharedInstance = null;

    /**
     * Stack of {@link Controller} instances that represent the current
     * application's navigation state.
     * On every frame update, the top-most <code>Controller</code> is used
     * to handle UI updates and application logic.
     */
    private List<Controller> navigationStack = new ArrayList<>();

    /**
     * Condition for application event loop.
     * @see #exit()
     */
    private boolean isRunning = true;

    private String appName = null;

    /**
     * Currently logged-in user's name.
     * TODO Replace with <code>User</code> model.
     */
    private String username = null;

    private App() {}

    /**
     * Singleton access point.
     * Instantiate the application if not yet instantiated.
     * @return Singleton {@link App}.
     */
    public static App shared() {
        if (sharedInstance == null)
            sharedInstance = new App();
        return sharedInstance;
    }

    public String getAppName() {
        return appName;
    }

    /**
     * Allow the app name to be set only once.
     */
    public void setAppName(String appName) {
        if (this.appName == null)
            this.appName = appName;
    }

    /**
     * Get the currently logged-in user's name.
     * If no user is logged in, return "anonymous".
     */
    public String getUsername() {
        if (username == null)
            return "anonymous";
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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
    public void popNavigation() {
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

    /**
     * Prevent the next frame from running.
     */
    public void exit() {
        isRunning = false;
    }

    /**
     * Initiate the event loop of the application.
     */
    public void run() {
        while (isRunning) {
            getCurrentController().run();
        }
        System.out.println("Goodbye!");
        System.exit(1);
    }
}
