package com.g1t11.socialmagnet.controller.socialmagnet;

import com.g1t11.socialmagnet.controller.Controller;
import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.model.social.User;

public abstract class SocialMagnetController extends Controller {
    User me;

    public SocialMagnetController(Navigator nav, User me) {
        super(nav);
        this.me = me;
    }
}
