package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.data.rest.CredentialsRestDAO;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.TextUtils;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.RegisterPageView;

public class RegisterController extends Controller {
    private CredentialsRestDAO credDAO = new CredentialsRestDAO();

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
        if (!validateUsername(username)) {
            return;
        }

        getView().showFullnamePrompt();
        String fullname = input.nextLine();
        if (!validateFullname(fullname)) {
            return;
        }

        getView().showPasswordPrompt();
        String password = input.readPassword();
        if (!validatePassword(password)) {
            return;
        }

        getView().showConfirmPasswordPrompt();
        String passwordCheck = input.readPassword();
        if (!validatePasswordCheck(password, passwordCheck)) {
            return;
        }

        credDAO.register(username, fullname, password);
        nav.pop();
        nav.setCurrStatus(Painter.paint(
                String.format("Registered %s successfully!", username),
                Color.GREEN));
    }

    private boolean validateUsername(String username) {
        if (username.length() == 0) {
            rejectRegistration("Username cannot be empty.");
            return false;
        }

        if (username.length() > 255) {
            rejectRegistration("Username is too long.");
            return false;
        }

        if (!TextUtils.isAlphanumeric(username)) {
            rejectRegistration(
                    "Username should only contain alphanumeric characters.");
            return false;
        }

        if (credDAO.userExists(username)) {
            rejectRegistration(String.format(
                    "%s already exists. Choose another username.", username));
            return false;
        }

        return true;
    }

    private boolean validateFullname(String fullname) {
        if (fullname.length() == 0) {
            rejectRegistration("Your name cannot be empty.");
            return false;
        }

        if (fullname.length() > 255) {
            rejectRegistration("Your name is too long.");
            return false;
        }

        return true;
    }

    private boolean validatePassword(String password) {
        if (password.length() == 0) {
            rejectRegistration("Password cannot be empty.");
            return false;
        }

        if (password.length() < 6) {
            rejectRegistration("Password must 6 characters or more.");
            return false;
        }

        return true;
    }

    private boolean validatePasswordCheck(String password, String check) {
        if (!password.equals(check)) {
            rejectRegistration("Passwords do not match.");
            return false;
        }

        return true;
    }

    private void rejectRegistration(String message) {
        nav.pop();
        nav.setCurrStatus(Painter.paint(message, Color.RED));
    }
}
