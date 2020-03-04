package com.g1t11.socialmagnet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.g1t11.socialmagnet.App;
import com.g1t11.socialmagnet.model.kit.NumberedAction;
import com.g1t11.socialmagnet.view.WelcomePageView;
import com.g1t11.socialmagnet.view.kit.*;

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
        PromptInput input = new PromptInput("Enter your choice");
        switch (input.nextLine()) {
            case "1":
                App.shared().getNavigation().prepareForNavigation(new RegisterPageController());
                break;
            case "2":
                App.shared().getNavigation().prepareForNavigation(new LoginPageController());
                break;
            case "3":
                App.shared().exit();
                break;
            default:
                displayErrorMessage("Please enter a choice between 1 & 3!");
                break;
        }
    }

    public void displayErrorMessage(String message) {
        view.setStatus(new TextView(message));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WelcomePageController)) return false;
        WelcomePageController other = (WelcomePageController) o;
        return Objects.equals(view, other.view);
    }
}
