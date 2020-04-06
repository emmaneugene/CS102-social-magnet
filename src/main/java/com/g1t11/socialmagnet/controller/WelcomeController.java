package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.WelcomePageView;

/**
 * This is the controller for Welcome page.
 */
public class WelcomeController extends Controller {
    /**
     * Creates a Welcome page Controller.
     * @param nav The app's navigator.
     */
    public WelcomeController(Navigator nav) {
        super(nav);
        setView(new WelcomePageView());
    }

    @Override
    public void handleInput() {
        getView().showMainPrompt();
        switch (input.nextLine()) {
            case "1":
                nav.push(new RegisterController(nav));
                break;
            case "2":
                nav.push(new LoginController(nav));
                break;
            case "3":
                System.out.println(Painter.paint(
                        "Goodbye!", Color.GREEN));
                System.exit(0);
                break;
            default:
                setStatus(Painter.paint(
                        "Please enter a choice between 1 & 3!",
                        Color.RED));
                break;
        }
    }
}
