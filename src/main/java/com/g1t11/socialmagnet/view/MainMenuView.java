package com.g1t11.socialmagnet.view;

import java.util.List;
import java.util.Objects;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.view.kit.*;

public class MainMenuView extends PageView {
    private TextView greetingView;

    private final List<TextView> actionViews = List.of(
        new TextView(Painter.paintf("[{1.}] News Feed", Painter.Color.YELLOW)),
        new TextView(Painter.paintf("[{2.}] My Wall", Painter.Color.YELLOW)),
        new TextView(Painter.paintf("[{3.}] My Friends", Painter.Color.YELLOW)),
        new TextView(Painter.paintf("[{4.}] City Farmers", Painter.Color.YELLOW)),
        new TextView(Painter.paintf("[{5.}] Logout", Painter.Color.YELLOW))
    );

    String fullname;

    public MainMenuView() {
        super("Main Menu");
        greetingView = new TextView();
    }

    @Override
    public void render() {
        super.render();
        updateGreeting();
        greetingView.render();
        for (TextView actionView : actionViews) {
            actionView.render();
        }
    }

    public void setFullname(String fullname) {
        assert fullname != null;
        this.fullname = fullname;
    }

    private void updateGreeting() {
        greetingView.setText(String.format("Welcome, %s!", fullname));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MainMenuView)) return false;
        MainMenuView other = (MainMenuView) o;
        return Objects.equals(greetingView, other.greetingView)
            && Objects.deepEquals(actionViews, other.actionViews);
    }
}
