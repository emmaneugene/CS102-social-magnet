package com.g1t11.socialmagnet.controller;

import java.util.Objects;

import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.WelcomePageView;

public class WelcomePageController extends Controller {
    public WelcomePageController() {
        view = new WelcomePageView();
    }

    @Override
    public void updateView() {
        String username = nav.getSession().getUser() == null ? "anonymous" : nav.getSession().getUser().getUsername();
        ((WelcomePageView) view).setUsername(username);
        view.render();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput();
        switch (input.nextLine()) {
            case "1":
                nav.prepareForNavigation(new RegisterPageController());
                break;
            case "2":
                nav.prepareForNavigation(new LoginPageController());
                break;
            case "3":
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                view.setStatus("Please enter a choice between 1 & 3!");
                break;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WelcomePageController)) return false;
        WelcomePageController other = (WelcomePageController) o;
        return Objects.equals(view, other.view);
    }
}
