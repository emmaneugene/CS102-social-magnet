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
import com.g1t11.socialmagnet.util.PromptInput;
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
        List<Plot> toStealPlots = farmerLoadDAO.getPlots(toStealFrom.getUsername(), toStealFrom.getMaxPlotCount());
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
            nav.setCurrStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        } else if (choice.equals("M")) {
            nav.popTo(MainMenuController.class);
        } else if (choice.equals("F")) {
            nav.popTo(CityFarmersMainMenuController.class);
        } else if (choice.equals("S")) {
            handleSteal();
        } else {
            nav.pop();
            nav.setCurrStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
    }

    private void handleSteal() {
        List<StealingRecord> stolenCrops = farmerActionDAO.steal(me.getUsername(), toStealFrom.getUsername());
        if (stolenCrops.size() == 0) {
            nav.pop();
            nav.setCurrStatus(Painter.paint("No plots available to steal from.", Painter.Color.RED));
        } else {
            String stolenCropsString = formatStolenCrops(stolenCrops);
            nav.pop();
            nav.setCurrStatus(String.format(
                Painter.paint("You have successfully stolen %s.", Painter.Color.GREEN),
                stolenCropsString
            ));
        }
    }

    private String formatStolenCrops(List<StealingRecord> stolenCrops) {
        int size = stolenCrops.size();
        if (size == 0) return "";
        if (size == 1) return stolenCrops.get(0).toString();
        StringBuilder sb = new StringBuilder(stolenCrops.get(0).toString());
        for (int i = 1; i < size - 1; i++) {
            sb.append(", ").append(stolenCrops.get(i).toString());
        }
        sb.append(", and ").append(stolenCrops.get(size - 1).toString());
        return sb.toString();
    }
}
