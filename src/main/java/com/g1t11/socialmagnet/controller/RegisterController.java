package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.data.NoConnectionException;
import com.g1t11.socialmagnet.util.InputValidator;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.RegisterPageView;

public class RegisterController extends Controller {
    public RegisterController(Navigation nav) {
        super(nav);
        view = new RegisterPageView();
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
        String password = input.readPassword();

        input.setPrompt("Confirm your password");
        String passwordCheck = input.readPassword();

        if (!password.equals(passwordCheck)) {
            nav.pop();
            nav.currentController().view.setStatus(Painter.paint("Passwords do not match.", Painter.Color.RED));
            return;
        }

        try {
            boolean registerSuccessful = nav.session().register(username, fullName, password);
            nav.pop();
            if (registerSuccessful) {
                nav.currentController().view.setStatus(String.format(Painter.paint("Registered %s successfully!", Painter.Color.GREEN), username));
            } else {
                nav.currentController().view.setStatus(String.format(Painter.paint("%s already exists. Choose another username.", Painter.Color.RED), username));
            }
        } catch (NoConnectionException e) {
            nav.pop();
            throw new NoConnectionException();
        }
    }
}
