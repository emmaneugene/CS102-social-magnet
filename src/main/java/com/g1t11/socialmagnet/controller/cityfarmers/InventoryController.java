package com.g1t11.socialmagnet.controller.cityfarmers;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.controller.socialmagnet.MainMenuController;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.cityfarmers.InventoryPageView;

public class InventoryController extends CityFarmersController {
    public InventoryController(Navigator nav, Farmer me) {
        super(nav, me);
        setView(new InventoryPageView(farmerLoadDAO.getInventoryCrops(me.getUsername())));
    }

    @Override
    public void handleInput() {
        input.setPrompt(Painter.paintf("[[{M}]]ain | City [[{F}]]armers", Color.YELLOW));

        String choice = input.nextLine();
        switch (choice) {
            case "M":
                nav.popTo(MainMenuController.class);
                break;
            case "F":
                nav.popTo(CityFarmersMainMenuController.class);
                break;
            default:
                setStatus(Painter.paint("Please select a valid option.", Color.RED));
        }
    }
}
