package controller;

import java.util.ArrayList;
import java.util.List;

import app.App;
import model.NumberedAction;
import util.ConsoleScanner;
import view.WelcomePageView;
import view.kit.TextView;

public class WelcomePageController extends Controller {
    private final List<NumberedAction> actions = new ArrayList<>(List.of(
        new NumberedAction("Register", "1"),
        new NumberedAction("Login", "2"),
        new NumberedAction("Exit", "3")
    ));

    private WelcomePageView view = new WelcomePageView(actions);

    @Override
    public void updateView() {
        view.render();
    }

    @Override
    public void handleInput() {
        String input = ConsoleScanner.shared().getInput();
        if (input.equals("1")) {
            App.shared().prepareForNavigation(new RegisterPageController());
        } else if (input.equals("3")) {
            App.shared().exit();
        } else {
            view.addPreView(new TextView("Please enter a choice between 1 & 3!"));
        }
    }
}
