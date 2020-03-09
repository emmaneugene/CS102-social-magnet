package com.g1t11.socialmagnet.controller;

import java.util.Objects;

import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.LoginPageView;
import com.g1t11.socialmagnet.view.kit.TextView;

public class LoginPageController extends Controller {

    private LoginPageView view = new LoginPageView();

    @Override
    public void updateView() {
        view.render();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput("Enter your username");
        String username = input.nextLine();

        input.setPrompt("Enter your password");
        String password = input.nextLine();

        boolean loginSuccessful = nav.getSession().login(username, password);
        if (loginSuccessful) {
            nav.pop();
        } else {
            nav.pop();
            nav.currentController().getView().setStatus("Login error! Please try again.", TextView.Color.RED);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LoginPageController)) return false;
        LoginPageController other = (LoginPageController) o;
        return Objects.equals(view, other.view);
    }
}
