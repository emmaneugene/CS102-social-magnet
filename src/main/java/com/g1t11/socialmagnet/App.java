package com.g1t11.socialmagnet;

import com.g1t11.socialmagnet.controller.Navigation;
import com.g1t11.socialmagnet.controller.WelcomePageController;
import com.g1t11.socialmagnet.data.Database;
import com.g1t11.socialmagnet.data.Session;

public class App {

    private Navigation nav = null;

    private Database db = null;

    private Session sesh = null;

    private App() {
        db = new Database();
        sesh = new Session(db.connection());
        nav = new Navigation(sesh);
        nav.setFirstController(new WelcomePageController());
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
