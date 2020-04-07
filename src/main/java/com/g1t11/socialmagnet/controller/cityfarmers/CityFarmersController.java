package com.g1t11.socialmagnet.controller.cityfarmers;

import com.g1t11.socialmagnet.controller.Controller;
import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.data.rest.FarmerActionRestDAO;
import com.g1t11.socialmagnet.data.rest.FarmerLoadRestDAO;
// import com.g1t11.socialmagnet.data.StoreDAO;
import com.g1t11.socialmagnet.data.rest.StoreRestDAO;
import com.g1t11.socialmagnet.data.rest.UserRestDAO;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.view.page.cityfarmers.CityFarmersPageView;

/**
 * This abstract class acts as the base controller of all City Farmer pages.
 */
public abstract class CityFarmersController extends Controller {
    protected UserRestDAO userDAO = new UserRestDAO();
    protected FarmerLoadRestDAO farmerLoadDAO = new FarmerLoadRestDAO();
    protected FarmerActionRestDAO farmerActionDAO = new FarmerActionRestDAO();
    // protected StoreDAO storeDAO = new StoreDAO(database());
    protected StoreRestDAO storeDAO = new StoreRestDAO();

    /** The current farmer being displayed in City Farmer */
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
    public CityFarmersPageView getView() {
        return (CityFarmersPageView) super.getView();
    }

    @Override
    public void updateView() {
        me = farmerLoadDAO.getFarmer(me.getUsername());
        // Ensures that the farmer is always updated before a page refresh.
        getView().setFarmer(me);
    }
}
