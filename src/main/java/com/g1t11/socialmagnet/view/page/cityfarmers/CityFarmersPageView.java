package com.g1t11.socialmagnet.view.page.cityfarmers;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.view.component.FarmerHeaderComponent;
import com.g1t11.socialmagnet.view.page.PageView;

public abstract class CityFarmersPageView extends PageView {
    Farmer me;
    FarmerHeaderComponent headerComp = new FarmerHeaderComponent();

    public CityFarmersPageView(String title) {
        super("City Farmers", title);
    }

    public CityFarmersPageView() {
        super("City Farmers");
    }

    public void setFarmer(Farmer me) {
        this.me = me;
        headerComp.setFarmer(me);
    }

    public Farmer getMe() {
        return me;
    }

    @Override
    public void display() {
        super.display();
        headerComp.render();
        System.out.println();
    }
}