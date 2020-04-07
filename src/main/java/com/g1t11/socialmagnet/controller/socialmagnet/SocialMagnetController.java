package com.g1t11.socialmagnet.controller.socialmagnet;

import com.g1t11.socialmagnet.controller.Controller;
import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.data.rest.ThreadActionRestDAO;
import com.g1t11.socialmagnet.data.rest.ThreadLoadRestDAO;
import com.g1t11.socialmagnet.data.rest.UserRestDAO;
import com.g1t11.socialmagnet.model.social.User;

/**
 * This abstract class acts as the base controller of all Social Magnet pages.
 */
public abstract class SocialMagnetController extends Controller {
    protected UserRestDAO userDAO = new UserRestDAO();
    protected ThreadLoadRestDAO threadLoadDAO = new ThreadLoadRestDAO();
    protected ThreadActionRestDAO threadActionDAO = new ThreadActionRestDAO();

    protected User me;

    public SocialMagnetController(Navigator nav, User me) {
        super(nav);
        this.me = me;
    }
}
