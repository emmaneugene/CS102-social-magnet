package com.g1t11.socialmagnet.view.page;

import com.g1t11.socialmagnet.util.Painter.Color;

public class LoginPageView extends PageView {
    public LoginPageView() {
        super("Login");
    }

    @Override
    public void display() {
        super.display();
    }

    public void showUsernamePrompt() {
        showPrompt("Enter your username");
        setInputColor(Color.BLUE);
    }

    public void showPasswordPrompt() {
        showPrompt("Enter your password");
    }
}
