package com.g1t11.socialmagnet.controller.cityfarmers;

import com.g1t11.socialmagnet.controller.Controller;
import com.g1t11.socialmagnet.controller.Navigation;
import com.g1t11.socialmagnet.data.FarmerDAO;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.view.page.cityfarmers.CityFarmersPageView;

public abstract class CityFarmersController extends Controller {
    FarmerDAO farmerDAO = new FarmerDAO(nav.database());

    Farmer me;

    public CityFarmersController(Navigation nav, Farmer me) {
        super(nav);
        this.me = me;
    }

    public CityFarmersController(Navigation nav, User me) {
        super(nav);
        this.me = farmerDAO.getFarmer(me);
    }

    @Override
    public void updateView() {
        me = farmerDAO.getFarmer(me);
        ((CityFarmersPageView) view).setFarmer(me);
    }
}