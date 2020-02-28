package controller;

import app.App;
import util.ConsoleScanner;
import view.RegisterPageView;

public class RegisterPageController extends Controller {

    private RegisterPageView view = new RegisterPageView();

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
