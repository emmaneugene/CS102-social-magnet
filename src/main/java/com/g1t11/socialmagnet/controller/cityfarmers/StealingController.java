package com.g1t11.socialmagnet.controller.cityfarmers;

import java.util.List;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.controller.socialmagnet.MainMenuController;
import com.g1t11.socialmagnet.data.FarmerActionDAO;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.farm.Plot;
import com.g1t11.socialmagnet.model.farm.StealingRecord;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.TextUtils;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.component.FriendFarmComponent;

public class StealingController extends CityFarmersController {
    FarmerActionDAO farmerActionDAO = new FarmerActionDAO(database());

    Farmer toStealFrom;

    FriendFarmComponent friendFarmComp;

    public StealingController(Navigator nav, Farmer me, User friend) {
        super(nav, me);
        toStealFrom = farmerLoadDAO.getFarmer(friend.getUsername());
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
        List<Plot> toStealPlots = farmerLoadDAO.getPlots(
                toStealFrom.getUsername(),
                toStealFrom.getMaxPlotCount());
        friendFarmComp.setPlots(toStealPlots);
        friendFarmComp.render();
    }

    @Override
    public void handleInput() {
        input.setPrompt(Painter.paintf(
                "[[{M}]]ain | City [[{F}]]armers | [[{S}]]teal",
                Color.YELLOW));

        String choice = input.nextLine();
        nav.pop();
        if (choice.length() == 0) {
            nav.setCurrStatus(Painter.paint("Please select a valid option.", Color.RED));
        } else if (choice.equals("M")) {
            nav.popTo(MainMenuController.class);
        } else if (choice.equals("F")) {
            nav.popTo(CityFarmersMainMenuController.class);
        } else if (choice.equals("S")) {
            handleSteal();
        } else {
            nav.setCurrStatus(Painter.paint("Please select a valid option.", Color.RED));
        }
    }

    private void handleSteal() {
        List<StealingRecord> stolenCrops = farmerActionDAO.steal(
                me.getUsername(),
                toStealFrom.getUsername());
        if (stolenCrops.size() == 0) {
            nav.setCurrStatus(Painter.paint("No plots available to steal from.", Color.RED));
            return;
        }
        String stolenCropsString = TextUtils.prettyList(stolenCrops);
        nav.setCurrStatus(Painter.paint(
                String.format("You have successfully stolen %s.", stolenCropsString),
                Color.GREEN));
    }
}
