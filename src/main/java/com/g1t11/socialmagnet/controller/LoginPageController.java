package com.g1t11.socialmagnet.controller;

import java.util.Objects;

import com.g1t11.socialmagnet.App;
import com.g1t11.socialmagnet.view.LoginPageView;

public class LoginPageController extends Controller {

    private LoginPageView view = new LoginPageView();

    @Override
    public void updateView() {
        view.render();
    }

    @Override
    public void handleInput() {
        PromptInput usernameInput = new PromptInput("Enter your username");
        String username = usernameInput.nextLine();

        PromptInput passwordInput = new PromptInput("Enter your password");
        String password = passwordInput.nextLine();

        boolean loginSuccessful = App.shared().getSession().login(username, password);
        if (loginSuccessful) {
            App.shared().getNavigation().pop();
        } else {
            App.shared().getNavigation().pop();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LoginPageController)) return false;
        LoginPageController other = (LoginPageController) o;
        return Objects.equals(view, other.view);
    }
}
