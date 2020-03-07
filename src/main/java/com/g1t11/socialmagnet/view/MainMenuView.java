package com.g1t11.socialmagnet.view;

import java.util.List;
import java.util.Objects;

import com.g1t11.socialmagnet.view.kit.*;

public class MainMenuView extends PageView {
    private TextView greetingView;

    private final List<TextView> actionViews = List.of(
        new TextView("1. News Feed"),
        new TextView("2. My Wall"),
        new TextView("3. My Friends"),
        new TextView("4. City Farmers"),
        new TextView("5. Logout")
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
