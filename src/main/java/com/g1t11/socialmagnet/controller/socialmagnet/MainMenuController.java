package com.g1t11.socialmagnet.controller.socialmagnet;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.controller.cityfarmers.CityFarmersMainMenuController;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.socialmagnet.MainMenuPageView;

public class MainMenuController extends SocialMagnetController {
    public MainMenuController(Navigator nav, User me) {
        super(nav, me);
        setView(new MainMenuPageView(me));
    }

    @Override
    public void handleInput() {
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
                setStatus(Painter.paint(
                        "Please enter a choice between 1 & 5!", Color.RED));
                break;
        }
    }
}
