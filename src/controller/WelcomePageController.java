package controller;

import java.util.ArrayList;
import java.util.List;

import model.NumberedAction;
import view.WelcomePageView;

public class WelcomePageController extends Controller {
    private final List<NumberedAction> actions = new ArrayList<>(List.of(
        new NumberedAction("Register", '1'),
        new NumberedAction("Login", '2'),
        new NumberedAction("Exit", '3')
    ));

    private WelcomePageView view;

    public WelcomePageController() {
        view = new WelcomePageView(actions);
    }

    public void updateView() {
        view.render();
    }

    public void getInput() {
    }
}
