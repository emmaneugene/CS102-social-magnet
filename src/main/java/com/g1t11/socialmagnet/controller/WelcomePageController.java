package com.g1t11.socialmagnet.controller;

import java.sql.Connection;
import java.util.Objects;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.WelcomePageView;

public class WelcomePageController extends Controller {
    public WelcomePageController(Connection conn) {
        super(conn);
        view = new WelcomePageView();
    }

    @Override
    public void updateView() {
        // Inject an updated username into the view
        String username = nav.getSession().currentUser() == null ? "anonymous" : nav.getSession().currentUser().getUsername();
        ((WelcomePageView) view).setUsername(username);
        view.render();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput();
        switch (input.nextLine()) {
            case "1":
                nav.push(new RegisterPageController(conn));
                break;
            case "2":
                nav.push(new LoginPageController(conn));
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WelcomePageController)) return false;
        WelcomePageController other = (WelcomePageController) o;
        return Objects.equals(view, other.view);
    }
}
