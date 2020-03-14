package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.LoginPageView;

public class LoginController extends Controller {
    public LoginController(Navigation nav) {
        super(nav);
        view = new LoginPageView();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput("Enter your username");
        String username = input.nextLine();

        input.setPrompt("Enter your password");
        String password = input.nextLine();

        boolean loginSuccessful = nav.session().login(username, password);
        nav.pop();
        if (loginSuccessful) {
            nav.push(new MainMenuController(nav));
        } else {
            nav.currentController().view.setStatus(Painter.paint("Login error! Please try again.", Painter.Color.RED));
        }
    }
}
