package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.MainMenuPageView;

public class MainMenuController extends Controller {
    public MainMenuController(Navigation nav) {
        super(nav);
        // Inject an updated full name into the view
        String fullname = nav.session().currentUser().getFullname();
        view = new MainMenuPageView(fullname);
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput();
        switch (input.nextLine()) {
            case "1":
                nav.push(new NewsFeedController(nav));
                break;
            case "2":
                nav.push(new WallController(nav));
                break;
            case "3":
                nav.push(new FriendsController(nav));
                break;
            case "4":
            case "5":
                nav.session().logout();
                nav.popToFirst();
                break;
            default:
                view.setStatus(Painter.paint("Please enter a choice between 1 & 5!", Painter.Color.RED));
                break;
        }
    }
}
