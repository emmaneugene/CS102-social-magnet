package com.g1t11.socialmagnet.view.page.cityfarmers;

import java.util.List;

import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;

/**
 * This is a page view for City Farmers' Visit Friend page.
 */
public class VisitFriendPageView extends CityFarmersPageView {
    List<User> friends;

    /**
     * Creates a Visit Friend page view with the specified list of friends that
     * the user have.
     * @param friends The list of friends that the user have.
     */
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
