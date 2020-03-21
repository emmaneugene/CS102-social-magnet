package com.g1t11.socialmagnet;

import com.g1t11.socialmagnet.controller.Navigation;
import com.g1t11.socialmagnet.controller.WelcomeController;
import com.g1t11.socialmagnet.data.Database;
import com.g1t11.socialmagnet.data.NoConnectionException;
import com.g1t11.socialmagnet.util.Painter;

public class App {
    public Database db = null;

    public Navigation nav = null;

    public App() {
        db = new Database();
        nav = new Navigation(db);
        nav.setFirstController(new WelcomeController(nav));
    }

    /**
     * Initiate the event loop of the application.
     */
    public void run() {
        while (true) {
            try {
                nav.currentController().run();
            } catch (NoConnectionException e) {
                nav.currentController().view.setStatus(Painter.paint("Failed to connect to database.", Painter.Color.RED));
                db.establishConnection();
            }
        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
}
