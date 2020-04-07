package com.g1t11.socialmagnet.view.page.cityfarmers;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.view.component.FarmerHeaderComponent;
import com.g1t11.socialmagnet.view.page.PageView;

/**
 * This is the general City Farmers Page view that will be used for the rest of 
 * the page views that is under City Farmers.
 */
public abstract class CityFarmersPageView extends PageView {
    protected Farmer me;
    protected FarmerHeaderComponent headerComp = new FarmerHeaderComponent();

    /**
     * Creates a City Farmers page view with specified title.
     * @param title The title to display.
     */
    public CityFarmersPageView(String title) {
        super("City Farmers", title);
    }

    /**
     * Creates a general City Farmers page view without any title.
     */
    public CityFarmersPageView() {
        super("City Farmers");
    }

    /**
     * Sets the farmer to the farmer of current user.
     * @param me The farmer of current user.
     */
    public void setFarmer(Farmer me) {
        this.me = me;
        headerComp.setFarmer(me);
    }

    /**
     * Gets the current farmer of current user.
     * @return The farmer of user.
     */
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
