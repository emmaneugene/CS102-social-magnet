package com.g1t11.socialmagnet.view.page.cityfarmers;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.view.component.FarmerHeaderComponent;
import com.g1t11.socialmagnet.view.page.PageView;

public abstract class CityFarmersPageView extends PageView {
    FarmerHeaderComponent headerComp;

    public CityFarmersPageView(Farmer me, String title) {
        super("City Farmers", title);
        headerComp = new FarmerHeaderComponent(me);
    }

    public CityFarmersPageView(Farmer me) {
        super("City Farmers");
        headerComp = new FarmerHeaderComponent(me);
    }

    @Override
    public void display() {
        super.display();
        headerComp.render();
        System.out.println();
    }

    public CityFarmersPageView() {
        super("City Farmers");
    }
}