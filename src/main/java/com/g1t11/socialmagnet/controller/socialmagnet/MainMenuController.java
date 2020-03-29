package com.g1t11.socialmagnet.controller.socialmagnet;

import com.g1t11.socialmagnet.controller.Navigation;
import com.g1t11.socialmagnet.controller.cityfarmers.CityFarmersMainMenuController;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.socialmagnet.MainMenuPageView;

public class MainMenuController extends SocialMagnetController {
    private User me;

    public MainMenuController(Navigation nav, User me) {
        super(nav, me);
        this.me = me;
        view = new MainMenuPageView(me);
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput();
        switch (input.nextLine()) {
            case "1":
                nav.push(new NewsFeedController(nav, me));
                break;
            case "2":
                nav.push(new WallController(nav, me));
                break;
            case "3":
                nav.push(new FriendsController(nav, me));
                break;
            case "4":
                nav.push(new CityFarmersMainMenuController(nav, me));
                break;
            case "5":
                nav.popToFirst();
                break;
            default:
                view.setStatus(Painter.paint("Please enter a choice between 1 & 5!", Painter.Color.RED));
                break;
        }
    }
}
