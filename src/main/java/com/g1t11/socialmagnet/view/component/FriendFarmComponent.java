package com.g1t11.socialmagnet.view.component;

import java.util.List;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.farm.Plot;

public class FriendFarmComponent implements Component {
    Farmer friend;

    FriendFarmerHeaderComponent headerComp;
    FarmlandComponent farmComp = new FarmlandComponent();

    public FriendFarmComponent(Farmer friend) {
        this.friend = friend;
        headerComp = new FriendFarmerHeaderComponent(friend);
    }

    public void setPlots(List<Plot> plots) {
        farmComp.setPlots(plots);
    }

    @Override
    public void render() {
        System.out.println();
        headerComp.render();
        farmComp.render();
        System.out.println();
    }
}
