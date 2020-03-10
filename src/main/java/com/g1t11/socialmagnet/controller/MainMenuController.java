package com.g1t11.socialmagnet.controller;

import java.sql.Connection;
import java.util.Objects;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.MainMenuView;

public class MainMenuController extends Controller {
    public MainMenuController(Connection conn) {
        super(conn);
        view = new MainMenuView();
    }

    @Override
    public void updateView() {
        // Inject an updated full name into the view
        String fullname = nav.getSession().currentUser().getFullname();
        ((MainMenuView) view).setFullname(fullname);
        view.render();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput();
        switch (input.nextLine()) {
            case "1":
                nav.push(new NewsFeedController(conn));
                break;
            case "2":
            case "3":
            case "4":
            case "5":
                nav.getSession().logout();
                nav.setFirstController(new WelcomePageController(conn));
                break;
            default:
                view.setStatus(Painter.paint("Please enter a choice between 1 & 3!", Painter.Color.RED));
                break;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MainMenuController)) return false;
        MainMenuController other = (MainMenuController) o;
        return Objects.equals(view, other.view);
    }
}
