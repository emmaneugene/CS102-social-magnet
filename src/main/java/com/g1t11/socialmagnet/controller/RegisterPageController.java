package com.g1t11.socialmagnet.controller;

import java.sql.Connection;

import com.g1t11.socialmagnet.util.InputValidator;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.RegisterPageView;

public class RegisterPageController extends Controller {
    public RegisterPageController(Connection conn) {
        super(conn);
        view = new RegisterPageView();
    }

    @Override
    public void updateView() {
        view.render();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput("Enter your username");
        String username = input.nextLine();

        if (!InputValidator.isAlphanumeric(username)) {
            nav.pop();
            nav.currentController().view.setStatus(Painter.paint("Username should only contain alphanumeric characters.", Painter.Color.RED));
            return;
        }

        input.setPrompt("Enter your full name");
        String fullName = input.nextLine();

        input.setPrompt("Enter your password");
        String password = input.nextLine();

        input.setPrompt("Confirm your password");
        String passwordCheck = input.nextLine();

        if (!password.equals(passwordCheck)) {
            nav.pop();
            nav.currentController().view.setStatus(Painter.paint("Passwords do not match.", Painter.Color.RED));
            return;
        }

        boolean registerSuccessful = nav.session().register(username, fullName, password);

        if (registerSuccessful) {
            nav.pop();
            nav.currentController().view.setStatus(String.format(Painter.paint("Registered %s successfully!", Painter.Color.GREEN), username));
        } else {
            nav.pop();
            nav.currentController().view.setStatus(String.format(Painter.paint("%s already exists. Choose another username.", Painter.Color.RED), username));
        }
    }
}
