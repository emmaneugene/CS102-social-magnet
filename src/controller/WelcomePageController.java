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
        switch (input) {
            case "1":
                App.shared().prepareForNavigation(new RegisterPageController());
                break;
            case "2":
                App.shared().prepareForNavigation(new LoginPageController());
                break;
            case "3":
                App.shared().exit();
                break;
            default:
                view.setStatus(new TextView("Please enter a choice between 1 & 3!"));
                break;
        }
    }
}
