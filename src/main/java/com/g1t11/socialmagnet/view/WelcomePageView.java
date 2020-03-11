package com.g1t11.socialmagnet.view;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.view.kit.*;

public class WelcomePageView extends PageView {
    private TextView greetingView;

    private final List<TextView> actionViews = List.of(
        new TextView(Painter.paintf("%{1.%} Register", Painter.Color.BOLD)),
        new TextView(Painter.paintf("%{2.%} Login", Painter.Color.BOLD)),
        new TextView(Painter.paintf("%{3.%} Exit", Painter.Color.BOLD))
    );

    String username;

    Integer fixedHourOfDay = null;

    public WelcomePageView() {
        super("Welcome");
        greetingView = new TextView();
    }

    public WelcomePageView(int fixedHourOfDay) {
        this();
        this.fixedHourOfDay = fixedHourOfDay;
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

    public void setUsername(String username) {
        assert username != null;
        this.username = username;
    }

    /**
     * Update {@link #greetingView} based on the time of day.
     */
    private void updateGreeting() {
        int hour;
        if (fixedHourOfDay == null) {
            Calendar cal = Calendar.getInstance();
            hour = cal.get(Calendar.HOUR_OF_DAY);
        } else {
            hour = fixedHourOfDay;
        }
        String timeOfDay = basedOnHour(hour);
        String greeting = String.format("Good %s, %s!", timeOfDay, username);
        greetingView.setText(greeting);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WelcomePageView)) return false;
        WelcomePageView other = (WelcomePageView) o;
        return Objects.equals(greetingView, other.greetingView)
            && Objects.deepEquals(actionViews, other.actionViews)
            && Objects.equals(fixedHourOfDay, other.fixedHourOfDay);
    }

    public String basedOnHour(int hour) {
        if (hour < 12) return "morning";
        if (hour < 19) return "afternoon";
        return "evening";
    }
}
