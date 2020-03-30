package com.g1t11.socialmagnet.controller.cityfarmers;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.controller.socialmagnet.MainMenuController;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.cityfarmers.InventoryPageView;

public class InventoryController extends CityFarmersController {
    public InventoryController(Navigator nav, Farmer me) {
        super(nav, me);
        view = new InventoryPageView(farmerDAO.getInventoryCrops(me.getUsername()));
        ((InventoryPageView) view).setFarmer(me);
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput(Painter.paintf(
            "[[{M}]]ain | City [[{F}]]armers",
            Painter.Color.YELLOW
        ));

        String choice = input.nextLine();
        switch (choice) {
            case "M":
                nav.popTo(MainMenuController.class);
                break;
            case "F":
                nav.popTo(CityFarmersMainMenuController.class);
                break;
            default:
                view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
    }
}
