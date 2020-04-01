package com.g1t11.socialmagnet.view.page;

import com.g1t11.socialmagnet.util.Painter.Color;

public class RegisterPageView extends PageView {
    public RegisterPageView() {
        super("Registration");
    }

    @Override
    public void display() {
        super.display();
    }

    public void showUsernamePrompt() {
        showPrompt("Enter your username");
        setInputColor(Color.BLUE);
    }

    public void showFullnamePrompt() {
        showPrompt("Enter your full name");
    }

    public void showPasswordPrompt() {
        showPrompt("Enter your password");
    }

    public void showConfirmPasswordPrompt() {
        showPrompt("Confirm your password");
    }
}
