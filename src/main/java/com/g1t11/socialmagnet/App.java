package com.g1t11.socialmagnet;

import com.g1t11.socialmagnet.controller.Navigation;
import com.g1t11.socialmagnet.controller.WelcomePageController;
import com.g1t11.socialmagnet.data.Database;
import com.g1t11.socialmagnet.data.UserDAO;
import com.g1t11.socialmagnet.data.Session;

public class App {
    private String appName = null;

    private Navigation navigation = null;

    private Database database = null;

    private Session session = null;

    /**
     * Condition for application event loop.
     * @see #exit()
     */
    private boolean isRunning = true;

    private App() {
        navigation = new Navigation();
        database = new Database();
        database.establishDefaultConnection();
        UserDAO userDao = new UserDAO(database.connection());
        session = new Session(userDao);
    }

    /**
     * Singleton instance representing the current running application.
     * Stores all state variables that are global to the application.
     */
    private static App sharedInstance = null;

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
        if (appName == null)
            return "";
        return appName;
    }

    /**
     * Allow the app name to be set only once.
     */
    public void setAppName(String appName) {
        if (this.appName == null)
            this.appName = appName;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public Database getDatabase() {
        return database;
    }

    public Session getSession() {
        return session;
    }

    public static void restart() {
        sharedInstance = null;
    }

    /**
     * Prevent the next frame from running.
     */
    public void exit() {
        isRunning = false;
        System.out.println("Goodbye!");
    }

    /**
     * Initiate the event loop of the application.
     */
    public void run() {
        while (isRunning) {
            getNavigation().runCurrentController();
        }
    }

    public static void main(String[] args) {
        App.shared().setAppName("Social Magnet");
        App.shared().navigation.setFirstController(new WelcomePageController());
        App.shared().run();
    }
}
