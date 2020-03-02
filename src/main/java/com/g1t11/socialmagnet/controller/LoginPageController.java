package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.App;
import com.g1t11.socialmagnet.view.LoginPageView;

public class LoginPageController extends Controller {

    private LoginPageView view = new LoginPageView();

    @Override
    public void updateView() {
        view.render();
    }

    @Override
    public void handleInput() {
        if (App.shared().input().next().equals("y")) {
            App.shared().getNavigation().pop();
        }
    }
}
