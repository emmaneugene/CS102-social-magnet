package com.g1t11.socialmagnet.controller;

import java.util.Objects;

import com.g1t11.socialmagnet.App;
import com.g1t11.socialmagnet.view.RegisterPageView;
import com.g1t11.socialmagnet.view.kit.*;

public class RegisterPageController extends Controller {

    private RegisterPageView view = new RegisterPageView();

    @Override
    public void updateView() {
        view.render();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput("Return?");

        if (input.next().equals("y")) {
            App.shared().getNavigation().pop();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RegisterPageController)) return false;
        RegisterPageController other = (RegisterPageController) o;
        return Objects.equals(view, other.view);
    }
}
