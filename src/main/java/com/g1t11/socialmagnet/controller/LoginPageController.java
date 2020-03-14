package com.g1t11.socialmagnet.controller;

import java.sql.Connection;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.LoginPageView;

public class LoginPageController extends Controller {
    public LoginPageController(Connection conn) {
        super(conn);
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

        boolean loginSuccessful = nav.session().login(username, password);
        if (loginSuccessful) {
            nav.push(new MainMenuController(conn));
        } else {
            nav.pop();
            nav.currentController().view.setStatus(Painter.paint("Login error! Please try again.", Painter.Color.RED));
        }
    }
}
