package com.g1t11.socialmagnet.controller.socialmagnet;

import com.g1t11.socialmagnet.controller.Controller;
import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.model.social.User;

/**
 * This abstract class acts as the base controller of all Social Magnet pages.
 */
public abstract class SocialMagnetController extends Controller {
    User me;

    /**
     * Creates a Social Magnet controller.
     * @param nav The app's navigator.
     * @param me The user.
     */
    public SocialMagnetController(Navigator nav, User me) {
        super(nav);
        this.me = me;
    }
}
