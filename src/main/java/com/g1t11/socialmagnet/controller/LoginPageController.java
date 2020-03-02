package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.App;
import com.g1t11.socialmagnet.util.ConsoleScanner;
import com.g1t11.socialmagnet.view.LoginPageView;

public class LoginPageController extends Controller {

    private LoginPageView view = new LoginPageView();

	@Override
	public void updateView() {
        view.render();
	}

	@Override
	public void handleInput() {
        if (ConsoleScanner.shared().getInput().equals("y")) {
            App.shared().popNavigation();
        }
	}
}
