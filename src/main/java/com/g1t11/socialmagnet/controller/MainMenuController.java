package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.MainMenuView;

public class MainMenuController extends Controller {
    MainMenuView view = new MainMenuView();

    @Override
    public void updateView() {
        view.render();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput();
        switch (input.nextLine()) {
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
                nav.getSession().logout();
                nav.setFirstController(new WelcomePageController());
                break;
        }
    }
}
