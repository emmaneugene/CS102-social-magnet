package com.g1t11.socialmagnet.view.page.cityfarmers;

import java.util.List;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;

public class VisitFriendPageView extends CityFarmersPageView {
    List<User> friends;

    public VisitFriendPageView(Farmer me, List<User> friends) {
        super(me, "Visit Friend");
        this.friends = friends;
    }

    @Override
    public void display() {
        super.display();
        System.out.println();

        System.out.println("My Friends:");
        int index = 1;
        for (User friend : friends) {
            System.out.printf(Painter.paintf(
                "[{%d.}] %s ([{%s}])\n",
                Painter.Color.YELLOW, Painter.Color.BLUE
            ), index++, friend.getFullname(), friend.getUsername());
        }
        System.out.println();
    }
}