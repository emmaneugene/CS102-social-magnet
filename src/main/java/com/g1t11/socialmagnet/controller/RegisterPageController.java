package com.g1t11.socialmagnet.controller;

import java.util.Objects;

import com.g1t11.socialmagnet.App;
import com.g1t11.socialmagnet.view.RegisterPageView;
import com.g1t11.socialmagnet.util.PromptInput;

public class RegisterPageController extends Controller {

    private RegisterPageView view = new RegisterPageView();

    @Override
    public void updateView() {
        view.render();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput("Enter your username");
        String username = input.nextLine();

        boolean isAlphanumeric = username != null && username.matches("^[a-zA-Z0-9]*$");
        if (!isAlphanumeric) {
            App.shared().navigation().pop();
            App.shared().navigation().currentController().getView().setStatus("Username should only contain alphanumeric characters.");
            return;
        }

        input.setPrompt("Enter your full name");
        String fullName = input.nextLine();

        input.setPrompt("Enter your password");
        String password = input.nextLine();

        input.setPrompt("Confirm your password");
        String passwordCheck = input.nextLine();

        if (!password.equals(passwordCheck)) {
            App.shared().navigation().pop();
            App.shared().navigation().currentController().getView().setStatus("Password does not match.");
            return;
        }

        boolean registerSuccessful = App.shared().session().register(username, fullName, password);

        if (registerSuccessful) {
            App.shared().navigation().pop();
            App.shared().navigation().currentController().getView().setStatus(username + " , your account is successfully created!");
        } else {
            App.shared().navigation().pop();
            App.shared().navigation().currentController().getView().setStatus("Username exists. Choose another username");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RegisterPageController)) return false;
        RegisterPageController other = (RegisterPageController) o;
        return Objects.equals(view, other.view);
    }
}
