package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.data.CredentialsDAO;
import com.g1t11.socialmagnet.util.InputValidator;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.RegisterPageView;

/**
 * This is the controller for Register page.
 */
public class RegisterController extends Controller {
    private CredentialsDAO credDAO = new CredentialsDAO(database());

    /**
     * Creates a Register page controller.
     * @param nav The app's navigator.
     */
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

    /**
     * This is a method to validate the input username. It will check the 
     * username for empty, too long, not an alphanumeric and existing username. 
     * It will then return false if the input username fits any of the
     * conditions previously stated. Else it will return true.
     * @param username The input username.
     * @return Whether if the username is valid.
     */
    private boolean validateUsername(String username) {
        if (username.length() == 0) {
            rejectRegistration("Username cannot be empty.");
            return false;
        }

        if (username.length() > 255) {
            rejectRegistration("Username is too long.");
            return false;
        }

        if (!InputValidator.isAlphanumeric(username)) {
            rejectRegistration(
                    "Username should only contain alphanumeric characters.");
            return false;
        }

        if (credDAO.userExists(username)) {
            rejectRegistration("%s already exists. Choose another username.");
            return false;
        }

        return true;
    }

    /**
     * This is a method to validate the input fullname. It will check the 
     * fullname for empty or too long. It will return false if the input 
     * fullname fits any of the conditions previously stated. Else it will 
     * return true.
     * @param fullname The input fullname.
     * @return Whether if the fullname is valid.
     */
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

    /**
     * This is a method to validate the input password. It will check the 
     * password for empty or too short. It will return false if the input 
     * fullname fits any of the conditions previously stated. Else it will
     * return true.
     * @param password The input password.
     * @return Whether if the password is valid.
     */
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

    /**
     * This is a method to validate the 2nd input password for the check. It 
     * will check if it matches with the previous password that the user input.
     * It will return true if it matches. Else it returns false.
     * @param password The input password before check.
     * @param check The input password after check.
     * @return Whetherif the 2nd password check is valid.
     */
    private boolean validatePasswordCheck(String password, String check) {
        if (!password.equals(check)) {
            rejectRegistration("Passwords do not match.");
            return false;
        }

        return true;
    }

    /**
     * A method to reject registration when any of the validation fails. It will
     * then pop to the previous navigation stack and set the status to display.
     * @param message The status to display.
     */
    private void rejectRegistration(String message) {
        nav.pop();
        nav.setCurrStatus(Painter.paint(message, Color.RED));
    }
}
