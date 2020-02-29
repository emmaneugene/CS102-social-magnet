package controller;

import app.App;
import util.ConsoleScanner;
import view.LoginPageView;

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
