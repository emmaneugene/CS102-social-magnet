package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.controller.cityfarmers.CityFarmersMainMenuController;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.MainMenuPageView;

public class MainMenuController extends Controller {
    public MainMenuController(Navigation nav) {
        super(nav);
        // Inject the user into the view
        view = new MainMenuPageView(nav.session().currentUser());
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
                nav.push(new CityFarmersMainMenuController(nav));
                break;
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
