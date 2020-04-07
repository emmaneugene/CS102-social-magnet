package com.g1t11.socialmagnet.controller.cityfarmers;

import java.util.List;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.controller.socialmagnet.MainMenuController;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.farm.Plot;
import com.g1t11.socialmagnet.model.farm.StealingRecord;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.util.TextUtils;
import com.g1t11.socialmagnet.view.page.cityfarmers.StealingPageView;

/**
 * This is a controller for the Stealing portion of visiting a friend's farm.
 */
public class StealingController extends CityFarmersController {
    Farmer toStealFrom;

    /**
     * Creates a Stealing controller.
     * @param nav The app's navigator.
     * @param me The farmer.
     * @param friend The friend that the farming is stealing from.
     */
    public StealingController(Navigator nav, Farmer me, User friend) {
        super(nav, me);
        toStealFrom = farmerLoadDAO.getFarmer(friend.getUsername());
        setView(new StealingPageView(toStealFrom));
    }

    @Override
    public StealingPageView getView() {
        return (StealingPageView) super.getView();
    }

    @Override
    public void updateView() {
        super.updateView();
        List<Plot> toStealPlots = farmerLoadDAO.getPlots(
                toStealFrom.getUsername(),
                toStealFrom.getMaxPlotCount());
        getView().setPlots(toStealPlots);
    }

    @Override
    public void handleInput() {
        getView().showMainPrompt();

        String choice = input.nextLine();
        nav.pop();
        if (choice.length() == 0) {
            nav.setCurrStatus(Painter.paint(
                    "Please select a valid option.", Color.RED));
        } else if (choice.equals("M")) {
            nav.popTo(MainMenuController.class);
        } else if (choice.equals("F")) {
            nav.popTo(CityFarmersMainMenuController.class);
        } else if (choice.equals("S")) {
            handleSteal();
        } else {
            nav.setCurrStatus(Painter.paint(
                    "Please select a valid option.", Color.RED));
        }
    }

    /**
     * A method to handle stealing. It will check if there is any plots
     * available to steal from.
     */
    private void handleSteal() {
        List<StealingRecord> stolenCrops = farmerActionDAO.steal(
                me.getUsername(),
                toStealFrom.getUsername());
        if (stolenCrops.size() == 0) {
            nav.setCurrStatus(Painter.paint(
                    "No plots available to steal from.", Color.RED));
            return;
        }
        String stolenCropsString = TextUtils.prettyList(stolenCrops);
        nav.setCurrStatus(Painter.paint(
                String.format("You have successfully stolen %s.",
                        stolenCropsString),
                Color.GREEN));
    }
}
