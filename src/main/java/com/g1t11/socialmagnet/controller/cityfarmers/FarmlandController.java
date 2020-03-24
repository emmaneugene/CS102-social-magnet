package com.g1t11.socialmagnet.controller.cityfarmers;

import com.g1t11.socialmagnet.controller.Controller;
import com.g1t11.socialmagnet.controller.MainMenuController;
import com.g1t11.socialmagnet.controller.Navigation;
import com.g1t11.socialmagnet.data.FarmerDAO;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.cityfarmers.FarmlandPageView;

public class FarmlandController extends Controller {
    FarmerDAO farmerDAO = new FarmerDAO(nav.database());

    Farmer me;

    public FarmlandController(Navigation nav, Farmer me) {
        super(nav);
        this.me = me;
        view = new FarmlandPageView(me);
    }

    @Override
    public void updateView() {
        ((FarmlandPageView) view).setPlotComps(farmerDAO.getPlots(me));
        view.display();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput(Painter.paintf(
            "[[{M}]]ain | City [[{F}]]armers | [[{P}]]lant | [[{C}]]lear | [[{H}]]arvest", 
            Painter.Color.YELLOW
        ));
        String choice = input.nextLine();
        if (choice.equals("M")) {
            nav.popTo(MainMenuController.class);
        } else if (choice.equals("F")) {
            nav.popTo(CityFarmersMainMenuController.class);
        } else {
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
    }
}