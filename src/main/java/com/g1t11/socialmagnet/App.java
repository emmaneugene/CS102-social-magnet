package com.g1t11.socialmagnet;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.controller.WelcomeController;
import com.g1t11.socialmagnet.data.ServerException;
import com.g1t11.socialmagnet.data.rest.RestDAO;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;

public class App {
    public Navigator nav = null;

    public App() {
        nav = new Navigator();
        nav.setFirstController(new WelcomeController(nav));
    }

    /**
     * Initiate the event loop of the application.
     */
    public void run() {
        while (true) {
            try {
                nav.currController().run();
            } catch (ServerException e) {
                handleDatabaseException(e);
            }
        }
    }

    private void handleDatabaseException(ServerException e) {
        Throwable cause = e.getCause();
        System.out.println(cause);
        nav.popToFirst();
        nav.setCurrStatus(Painter.paint(
                "Failed to connect to server.", Color.RED));
    }

    public static void main(String[] args) {
        RestDAO.BASE_URL = "http://" + args[0];
        App app = new App();
        app.run();
    }
}
