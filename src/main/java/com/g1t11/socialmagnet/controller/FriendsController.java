package com.g1t11.socialmagnet.controller;

import com.g1t11.socialmagnet.view.page.FriendsPageView;

public class FriendsController extends Controller {
    public FriendsController(Navigation nav) {
        super(nav);
        view = new FriendsPageView();
    }

    @Override
    public void handleInput() {
        // TODO Auto-generated method stub
    }

}