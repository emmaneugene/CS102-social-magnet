package com.g1t11.socialmagnet;

import com.g1t11.socialmagnet.controller.Navigation;
import com.g1t11.socialmagnet.controller.WelcomeController;
import com.g1t11.socialmagnet.data.Database;

public class App {
    public Database db = null;

    public Navigation nav = null;

    public App() {
        db = new Database();
        nav = new Navigation(db.connection());
        nav.setFirstController(new WelcomeController(nav));
    }

    /**
     * Initiate the event loop of the application.
     */
    public void run() {
        while (true) {
            nav.currentController().run();
        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
}
