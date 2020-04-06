package com.g1t11.socialmagnet.view.page.cityfarmers;

import java.util.List;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.farm.Plot;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.component.FarmlandComponent;
import com.g1t11.socialmagnet.view.component.FriendFarmerHeaderComponent;

/**
 * This is a page view for City Farmers' Stealing page.
 */
public class StealingPageView extends CityFarmersPageView {
    Farmer friend;

    FriendFarmerHeaderComponent headerComp;
    FarmlandComponent farmComp = new FarmlandComponent();

    /**
     * Creates a Stealing page view with specified friend that user is stealing
     * from.
     * @param friend The farmer that user is stealing from.
     */
    public StealingPageView(Farmer friend) {
        this.friend = friend;
        headerComp = new FriendFarmerHeaderComponent(friend);
    }

    /**
     * Sets a list of plots availble for stealing.
     * @param plots The list of plots availble for stealing.
     */
    public void setPlots(List<Plot> plots) {
        farmComp.setPlots(plots);
    }

    @Override
    public void clearScreen() {
        System.out.print(Color.RESET);
        // Disable screen refresh for this page view.
    }

    @Override
    public void display() {
        System.out.println();
        headerComp.render();
        farmComp.render();
        System.out.println();
    }

    @Override
    public void showMainPrompt() {
        showPrompt(Painter.paintf(
                "[[{M}]]ain | City [[{F}]]armers | [[{S}]]teal",
                Color.YELLOW));
        setInputColor(Color.YELLOW);
    }
}
