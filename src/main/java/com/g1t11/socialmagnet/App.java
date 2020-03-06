package com.g1t11.socialmagnet;

import com.g1t11.socialmagnet.controller.Navigation;
import com.g1t11.socialmagnet.controller.WelcomePageController;
import com.g1t11.socialmagnet.data.Database;
import com.g1t11.socialmagnet.data.Session;

public class App {

    public Database db = null;

    public Session session = null;

    public Navigation nav = null;

    public App() {
        db = new Database();
        session = new Session(db.connection());
        nav = new Navigation(session);
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
