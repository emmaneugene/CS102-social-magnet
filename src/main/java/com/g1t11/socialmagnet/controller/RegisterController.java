package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.data.CredentialsDAO;
import com.g1t11.socialmagnet.util.InputValidator;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.RegisterPageView;

public class RegisterController extends Controller {
    private CredentialsDAO credDAO = new CredentialsDAO(database());

    public RegisterController(Navigator nav) {
        super(nav);
        setView(new RegisterPageView());
    }

    @Override
    public RegisterPageView getView() {
        return (RegisterPageView) super.getView();
    }

    @Override
    public void handleInput() {
        getView().showUsernamePrompt();
        String username = input.nextLine();

        if (!InputValidator.isAlphanumeric(username)) {
            nav.pop();
            nav.setCurrStatus(Painter.paint(
                "Username should only contain alphanumeric characters.",
                Color.RED));
            return;
        }

        if (credDAO.userExists(username)) {
            nav.pop();
            nav.setCurrStatus(Painter.paint(
                    String.format("%s already exists. Choose another username.",
                            username),
                    Color.RED));
            return;
        }

        getView().showFullnamePrompt();
        String fullname = input.nextLine();

        getView().showPasswordPrompt();
        String password = input.readPassword();

        getView().showConfirmPasswordPrompt();
        String passwordCheck = input.readPassword();

        if (!password.equals(passwordCheck)) {
            nav.pop();
            nav.setCurrStatus(Painter.paint(
                    "Passwords do not match.", Color.RED));
            return;
        }

        credDAO.register(username, fullname, password);
        nav.pop();
        nav.setCurrStatus(Painter.paint(
                String.format("Registered %s successfully!", username),
                Color.GREEN));
    }
}
