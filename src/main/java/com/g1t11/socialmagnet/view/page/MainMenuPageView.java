package com.g1t11.socialmagnet.view.page;

import java.util.List;
import java.util.Objects;

import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;

public class MainMenuPageView extends PageView {
    private String greeting;

    private final List<String> actions = List.of(
        Painter.paintf("[{1.}] News Feed", Painter.Color.YELLOW),
        Painter.paintf("[{2.}] My Wall", Painter.Color.YELLOW),
        Painter.paintf("[{3.}] My Friends", Painter.Color.YELLOW),
        Painter.paintf("[{4.}] City Farmers", Painter.Color.YELLOW),
        Painter.paintf("[{5.}] Logout", Painter.Color.YELLOW)
    );

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MainMenuPageView)) return false;
        MainMenuPageView other = (MainMenuPageView) o;
        return Objects.equals(greeting, other.greeting)
            && Objects.deepEquals(actions, other.actions);
    }
}
