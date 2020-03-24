package com.g1t11.socialmagnet.view.page.cityfarmers;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.view.component.FarmerHeaderComponent;
import com.g1t11.socialmagnet.view.page.PageView;

public abstract class CityFarmersPageView extends PageView {
    Farmer me;
    FarmerHeaderComponent headerComp;

    public CityFarmersPageView(Farmer me, String title) {
        super("City Farmers", title);
        this.me = me;
        headerComp = new FarmerHeaderComponent(me);
    }

    public CityFarmersPageView(Farmer me) {
        super("City Farmers");
        this.me = me;
        headerComp = new FarmerHeaderComponent(me);
    }

    public void setFarmer(Farmer me) {
        this.me = me;
        headerComp = new FarmerHeaderComponent(me);
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

    public CityFarmersPageView() {
        super("City Farmers");
    }
}