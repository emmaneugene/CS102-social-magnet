package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.controller.socialmagnet.MainMenuController;
import com.g1t11.socialmagnet.data.rest.CredentialsRestDAO;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.LoginPageView;

public class LoginController extends Controller {
    private CredentialsRestDAO credDAO = new CredentialsRestDAO();

    public LoginController(Navigator nav) {
        super(nav);
        setView(new LoginPageView());
    }

    @Override
    public LoginPageView getView() {
        return (LoginPageView) super.getView();
    }

    @Override
    public void handleInput() {
        getView().showUsernamePrompt();
        String username = input.nextLine();

        getView().showPasswordPrompt();
        String password = input.readPassword();

        User me = credDAO.login(username, password);
        nav.popToFirst();
        if (me == null) {
            nav.setCurrStatus(Painter.paint(
                    "Login error! Please try again.", Color.RED));
        } else {
            nav.push(new MainMenuController(nav, me));
        }
    }
}
