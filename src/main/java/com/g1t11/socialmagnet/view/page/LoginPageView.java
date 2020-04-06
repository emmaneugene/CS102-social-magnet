package com.g1t11.socialmagnet.view.page;

import com.g1t11.socialmagnet.util.Painter.Color;

/**
 * This is the page view for Login page.
 */
public class LoginPageView extends PageView {

    /**
     * Creates a Login in page view.
     */
    public LoginPageView() {
        super("Login");
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
     * A method to promp user for password.
     */
    public void showPasswordPrompt() {
        showPrompt("Enter your password");
    }
}
