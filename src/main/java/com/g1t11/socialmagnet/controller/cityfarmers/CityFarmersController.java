package com.g1t11.socialmagnet.controller.cityfarmers;

import com.g1t11.socialmagnet.controller.Controller;
import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.data.FarmerLoadDAO;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.view.page.cityfarmers.CityFarmersPageView;

public abstract class CityFarmersController extends Controller {
    FarmerLoadDAO farmerLoadDAO = new FarmerLoadDAO(nav.database());

    Farmer me;

    public CityFarmersController(Navigator nav, Farmer me) {
        super(nav);
        this.me = me;
    }

    public CityFarmersController(Navigator nav, User me) {
        super(nav);
        this.me = farmerLoadDAO.getFarmer(me.getUsername());
    }

    @Override
    public void updateView() {
        me = farmerLoadDAO.getFarmer(me.getUsername());
        ((CityFarmersPageView) view).setFarmer(me);
    }
}
