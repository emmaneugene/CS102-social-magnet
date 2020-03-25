package com.g1t11.socialmagnet.controller.cityfarmers;

import com.g1t11.socialmagnet.controller.Controller;
import com.g1t11.socialmagnet.controller.Navigation;
import com.g1t11.socialmagnet.data.FarmerDAO;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.cityfarmers.CityFarmersMainMenuPageView;

public class CityFarmersMainMenuController extends Controller {
    FarmerDAO farmerDAO = new FarmerDAO(nav.database());

    Farmer me;

    public CityFarmersMainMenuController(Navigation nav) {
        super(nav);
        me = farmerDAO.getFarmer(nav.session().currentUser());
        view = new CityFarmersMainMenuPageView(me);
    }

    @Override
    public void updateView() {
        view.display();
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
            case "3":
            case "4":
            case "5":
            case "M":
                nav.pop();
                break;
            default:
                view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
    }
}