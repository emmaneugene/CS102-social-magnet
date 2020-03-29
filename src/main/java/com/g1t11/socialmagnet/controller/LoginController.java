package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.controller.socialmagnet.MainMenuController;
import com.g1t11.socialmagnet.data.CredentialsDAO;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.LoginPageView;

public class LoginController extends Controller {
    private CredentialsDAO credDAO = new CredentialsDAO(database());

    public LoginController(Navigation nav) {
        super(nav);
        view = new LoginPageView();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput("Enter your username");
        String username = input.nextLine();

        input.setPrompt("Enter your password");
        String password = input.readPassword();

        User me = credDAO.login(username, password);
        nav.popToFirst();
        if (me == null) {
            nav.currentController().view.setStatus(Painter.paint("Login error! Please try again.", Painter.Color.RED));
        } else {
            nav.push(new MainMenuController(nav, me));
        }
    }
}
