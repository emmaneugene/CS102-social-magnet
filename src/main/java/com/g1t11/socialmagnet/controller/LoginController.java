package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.controller.socialmagnet.MainMenuController;
import com.g1t11.socialmagnet.data.CredentialsDAO;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.view.page.LoginPageView;

public class LoginController extends Controller {
    private CredentialsDAO credDAO = new CredentialsDAO(database());

    public LoginController(Navigator nav) {
        super(nav);
        setView(new LoginPageView());
    }

    @Override
    public void handleInput() {
        input.setPrompt("Enter your username");
        String username = input.nextLine();

        input.setPrompt("Enter your password");
        String password = input.readPassword();

        User me = credDAO.login(username, password);
        nav.popToFirst();
        if (me == null) {
            nav.setCurrStatus(Painter.paint("Login error! Please try again.", Painter.Color.RED));
        } else {
            nav.push(new MainMenuController(nav, me));
        }
    }
}
