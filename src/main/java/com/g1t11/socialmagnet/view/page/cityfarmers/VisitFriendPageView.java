package com.g1t11.socialmagnet.view.page.cityfarmers;

import java.util.List;

import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;

public class VisitFriendPageView extends CityFarmersPageView {
    private List<User> friends;

    public VisitFriendPageView(List<User> friends) {
        super("Visit Friend");
        this.friends = friends;
    }

    @Override
    public void display() {
        super.display();

        System.out.println("My Friends:");
        int index = 1;
        for (User friend : friends) {
            System.out.printf(Painter.paintf(
                "[{%d.}] %s ([{%s}])\n",
                Color.YELLOW, Color.BLUE
            ), index++, friend.getFullname(), friend.getUsername());
        }
        System.out.println();
    }

    @Override
    public void showMainPrompt() {
        showPrompt(Painter.paintf(
                "[[{M}]]ain | City [[{F}]]armers | Select choice",
                Color.YELLOW));
        setInputColor(Color.YELLOW);
    }
}
