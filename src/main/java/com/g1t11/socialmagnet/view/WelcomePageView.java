package com.g1t11.socialmagnet.view;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import com.g1t11.socialmagnet.App;
import com.g1t11.socialmagnet.util.Greeting;
import com.g1t11.socialmagnet.view.kit.*;

public class WelcomePageView extends PageView {
    TextView greetingView;

    private final ListView actionsView = new ListView(List.of(
        new TextView("1. Register"),
        new TextView("2. Login"),
        new TextView("3. Exit")
    ));

    Integer fixedHourOfDay = null;

    public WelcomePageView() {
        super("Welcome");
        greetingView = new TextView("Good morning, anonymous!");
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
        actionsView.render();
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
        String time = Greeting.basedOnHour(hour);
        String greeting = String.format("Good %s, %s!", time, App.shared().session().getUsername());
        greetingView.setText(greeting);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WelcomePageView)) return false;
        WelcomePageView other = (WelcomePageView) o;
        return Objects.equals(greetingView, other.greetingView)
            && Objects.equals(actionsView, other.actionsView)
            && Objects.equals(fixedHourOfDay, other.fixedHourOfDay);
    }

    @Override
    public String toString() {
        return String.join("\n",
            super.toString(),
            greetingView.toString(),
            actionsView.toString()
        );
    }
}
