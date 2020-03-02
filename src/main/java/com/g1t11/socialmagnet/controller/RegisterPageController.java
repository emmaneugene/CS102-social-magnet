package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.App;
import com.g1t11.socialmagnet.util.ConsoleScanner;
import com.g1t11.socialmagnet.view.RegisterPageView;

public class RegisterPageController extends Controller {

    private RegisterPageView view = new RegisterPageView();

    @Override
    public void updateView() {
        view.render();
    }

    @Override
    public void handleInput() {
        if (ConsoleScanner.shared().getInput().equals("y")) {
            App.shared().getNavigation().pop();
        }
    }
}
