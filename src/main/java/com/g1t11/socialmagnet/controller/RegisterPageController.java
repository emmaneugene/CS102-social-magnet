package com.g1t11.socialmagnet.controller;

import java.util.Objects;

import com.g1t11.socialmagnet.util.InputValidator;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.RegisterPageView;

public class RegisterPageController extends Controller {
    public RegisterPageController() {
        view = new RegisterPageView();
    }

    @Override
    public void updateView() {
        view.render();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput("Enter your username");
        String username = input.nextLine();

        if (!InputValidator.isAlphanumeric(username)) {
            nav.pop();
            nav.currentController().getView().setStatus("Username should only contain alphanumeric characters.");
            return;
        }

        input.setPrompt("Enter your full name");
        String fullName = input.nextLine();

        input.setPrompt("Enter your password");
        String password = input.nextLine();

        input.setPrompt("Confirm your password");
        String passwordCheck = input.nextLine();

        if (!password.equals(passwordCheck)) {
            nav.pop();
            nav.currentController().getView().setStatus("Passwords do not match.");
            return;
        }

        boolean registerSuccessful = nav.getSession().register(username, fullName, password);

        if (registerSuccessful) {
            nav.pop();
            nav.currentController().getView().setStatus(String.format("Registered %s successfully!", username));
        } else {
            nav.pop();
            nav.currentController().getView().setStatus(String.format("%s already exists. Choose another username.", username));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RegisterPageController)) return false;
        RegisterPageController other = (RegisterPageController) o;
        return Objects.equals(view, other.view);
    }
}
