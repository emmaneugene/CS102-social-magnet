package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.WelcomePageView;

public class WelcomeController extends Controller {
    public WelcomeController(Navigation nav) {
        super(nav);
        view = new WelcomePageView();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput();
        switch (input.nextLine()) {
            case "1":
                nav.push(new RegisterController(nav));
                break;
            case "2":
                nav.push(new LoginController(nav));
                break;
            case "3":
                System.out.println(Painter.paint("Goodbye!", Painter.Color.GREEN));
                System.exit(0);
                break;
            default:
                view.setStatus(Painter.paint("Please enter a choice between 1 & 3!", Painter.Color.RED));
                break;
        }
    }
}