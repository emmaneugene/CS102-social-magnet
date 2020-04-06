package com.g1t11.socialmagnet.view.page.socialmagnet;

import java.util.List;
import java.util.Objects;

import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.PageView;

/**
 * This is a page view for Main Menu page after user logging in.
 */
public class MainMenuPageView extends PageView {
    private String greeting;

    private final List<String> actions = List.of(
        Painter.paintf("[{1.}] News Feed", Color.YELLOW),
        Painter.paintf("[{2.}] My Wall", Color.YELLOW),
        Painter.paintf("[{3.}] My Friends", Color.YELLOW),
        Painter.paintf("[{4.}] City Farmers", Color.YELLOW),
        Painter.paintf("[{5.}] Logout", Color.YELLOW)
    );

    /**
     * Creates a Main Menu page view with specified current user.
     * @param currentUser The current user.
     */
    public MainMenuPageView(User currentUser) {
        super("Main Menu");
        greeting = String.format("Welcome, %s!", currentUser.getFullname());
    }

    @Override
    public void display() {
        super.display();
        System.out.println(greeting);
        for (String action : actions) {
            System.out.println(action);
        }
    }

    /**
     * Compares the specified object with this MainMenuPageView for equality.
     * It returns true if and only if specified object is a MainMenuPageView and 
     * both MainMenuPageViews have the same greeting, action.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MainMenuPageView)) return false;
        MainMenuPageView other = (MainMenuPageView) o;
        return Objects.equals(greeting, other.greeting)
            && Objects.deepEquals(actions, other.actions);
    }
}
