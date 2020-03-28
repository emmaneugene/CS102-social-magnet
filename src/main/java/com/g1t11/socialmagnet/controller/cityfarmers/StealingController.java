package com.g1t11.socialmagnet.controller.cityfarmers;

import java.util.List;

import com.g1t11.socialmagnet.controller.Controller;
import com.g1t11.socialmagnet.controller.MainMenuController;
import com.g1t11.socialmagnet.controller.Navigation;
import com.g1t11.socialmagnet.data.FarmerDAO;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.farm.Plot;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.component.FriendFarmComponent;

public class StealingController extends Controller {
    FarmerDAO farmerDAO = new FarmerDAO(database());

    Farmer toStealFrom;

    FriendFarmComponent friendFarmComp;

    public StealingController(Navigation nav, User friend) {
        super(nav);
        toStealFrom = farmerDAO.getFarmer(friend);
        friendFarmComp = new FriendFarmComponent(toStealFrom);
    }

    /**
     * This controller is special in that it does not refresh the page.
     * Therefore, we will not be updating the view by rendering the PageView.
     * <p>
     * Instead, we will only render a {@link FriendFarmComponent}.
     */
    @Override
    public void updateView() {
        List<Plot> toStealPlots = farmerDAO.getPlots(toStealFrom);
        friendFarmComp.setPlots(toStealPlots);
        friendFarmComp.render();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput(Painter.paintf(
            "[[{M}]]ain | City [[{F}]]armers | [[{S}]]teal",
            Painter.Color.YELLOW
        ));
        String choice = input.nextLine();
        if (choice.length() == 0) {
            nav.pop();
            nav.currentController().view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        } else if (choice.equals("M")) {
            nav.popTo(MainMenuController.class);
        } else if (choice.equals("F")) {
            nav.popTo(CityFarmersMainMenuController.class);
        } else if (choice.matches("-?\\d+")) {
            handleSteal();
        } else {
            nav.pop();
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
    }

    private void handleSteal() {

    }

}