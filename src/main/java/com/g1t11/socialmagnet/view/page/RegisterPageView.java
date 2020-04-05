package com.g1t11.socialmagnet.view.page;

import com.g1t11.socialmagnet.util.Painter.Color;

/**
 * This is the page view for Registration page.
 */
public class RegisterPageView extends PageView {
    public RegisterPageView() {
        super("Registration");
    }

    @Override
    public void display() {
        super.display();
    }
    /**
     * A method to promp user for username.
     */
    public void showUsernamePrompt() {
        showPrompt("Enter your username");
        setInputColor(Color.BLUE);
    }

    /**
     * A method to promp user for full name.
     */
    public void showFullnamePrompt() {
        showPrompt("Enter your full name");
    }

    /**
     * A method to promp user for password.
     */
    public void showPasswordPrompt() {
        showPrompt("Enter your password");
    }

    /**
     * A method to promp user for password again for conformation.
     */
    public void showConfirmPasswordPrompt() {
        showPrompt("Confirm your password");
    }
}
