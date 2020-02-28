package controller;

import view.RegisterPageView;

public class RegisterPageController extends Controller {

    private RegisterPageView view = new RegisterPageView();

	@Override
	public void updateView() {
        view.render();
	}

	@Override
	public void handleInput() {
	}
}
