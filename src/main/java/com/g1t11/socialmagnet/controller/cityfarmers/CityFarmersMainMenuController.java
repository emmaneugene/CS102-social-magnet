package com.g1t11.socialmagnet.controller.cityfarmers;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.data.FarmerDAO;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.cityfarmers.CityFarmersMainMenuPageView;

public class CityFarmersMainMenuController extends CityFarmersController {
    FarmerDAO farmerDAO = new FarmerDAO(nav.database());

    public CityFarmersMainMenuController(Navigator nav, User me) {
        super(nav, me);
        view = new CityFarmersMainMenuPageView();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput(Painter.paintf(
            "[[{M}]]ain | Enter your choice", 
            Painter.Color.YELLOW
        ));
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
                view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
    }
}
