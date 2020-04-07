package com.g1t11.socialmagnet.view.page.cityfarmers;

import java.util.List;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.farm.Plot;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.component.FarmlandComponent;
import com.g1t11.socialmagnet.view.component.FriendFarmerHeaderComponent;

public class StealingPageView extends CityFarmersPageView {
    private FriendFarmerHeaderComponent headerComp;

    private FarmlandComponent farmComp = new FarmlandComponent();

    public StealingPageView(Farmer friend) {
        setFarmer(friend);
        headerComp = new FriendFarmerHeaderComponent(friend);
    }

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
