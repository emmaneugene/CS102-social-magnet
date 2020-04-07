package com.g1t11.socialmagnet.controller.cityfarmers;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.cityfarmers.CityFarmersMainMenuPageView;

/**
 * This is a controller for City Farmers' Main Menu.
 */
public class CityFarmersMainMenuController extends CityFarmersController {
    /**
     * Creates a City Farmers' Main Menu controller.
     * @param nav The app's navigator.
     * @param me The user.
     */
    public CityFarmersMainMenuController(Navigator nav, User me) {
        super(nav, me);
        setView(new CityFarmersMainMenuPageView());
    }

    @Override
    public void handleInput() {
        getView().showMainPrompt();

        String choice = input.nextLine();
        switch (choice) {
            case "1":
                nav.push(new FarmlandController(nav, me));
                break;
            case "2":
                nav.push(new StoreController(nav, me));
                break;
            case "3":
                nav.push(new InventoryController(nav, me));
                break;
            case "4":
                nav.push(new VisitFriendController(nav, me));
                break;
            case "5":
                nav.push(new SendGiftController(nav, me));
                break;
            case "M":
                nav.pop();
                break;
            default:
                setStatus(Painter.paint(
                        "Please select a valid option.", Color.RED));
        }
    }
}
