package com.g1t11.socialmagnet.controller;

import java.util.Objects;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.LoginPageView;

public class LoginPageController extends Controller {
    public LoginPageController() {
        view = new LoginPageView();
    }

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
            nav.prepareForNavigation(new MainMenuController());
        } else {
            nav.pop();
            nav.currentController().getView().setStatus(Painter.paint("Login error! Please try again.", Painter.Color.RED));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LoginPageController)) return false;
        LoginPageController other = (LoginPageController) o;
        return Objects.equals(view, other.view);
    }
}
