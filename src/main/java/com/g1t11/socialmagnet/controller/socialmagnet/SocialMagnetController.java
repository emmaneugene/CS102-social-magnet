package com.g1t11.socialmagnet.controller.socialmagnet;

import com.g1t11.socialmagnet.controller.Controller;
import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.data.ThreadActionDAO;
import com.g1t11.socialmagnet.data.ThreadLoadDAO;
import com.g1t11.socialmagnet.data.UserDAO;
import com.g1t11.socialmagnet.model.social.User;

/**
 * This abstract class acts as the base controller of all Social Magnet pages.
 */
public abstract class SocialMagnetController extends Controller {
    protected UserDAO userDAO = new UserDAO(database());
    protected ThreadLoadDAO threadLoadDAO = new ThreadLoadDAO(database());
    protected ThreadActionDAO threadActionDAO = new ThreadActionDAO(database());

    User me;

    public SocialMagnetController(Navigator nav, User me) {
        super(nav);
        this.me = me;
    }
}
