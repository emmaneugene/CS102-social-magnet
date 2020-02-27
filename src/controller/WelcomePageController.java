package controller;

import java.util.ArrayList;
import java.util.List;

import model.NumberedAction;
import view.WelcomePageView;

public class WelcomePageController {
    private List<NumberedAction> actions = new ArrayList<>();

    WelcomePageView view;

    public WelcomePageController() {
        actions.add(new NumberedAction("Register", '1'));
        actions.add(new NumberedAction("Login", '2'));
        actions.add(new NumberedAction("Exit", '3'));

        view = new WelcomePageView("Welcome", "Good morning, anonymous!", actions);
    }

    public void updateView() {
        view.render();
    }
}
