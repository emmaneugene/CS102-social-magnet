package com.g1t11.socialmagnet.view.page;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import com.g1t11.socialmagnet.util.Painter;

public class WelcomePageView extends PageView {
    private String greeting = "";

    private final List<String> actions = List.of(
        Painter.paintf("[{1.}] Register", Painter.Color.YELLOW),
        Painter.paintf("[{2.}] Login", Painter.Color.YELLOW),
        Painter.paintf("[{3.}] Exit", Painter.Color.YELLOW)
    );

    Integer fixedHourOfDay = null;

    public WelcomePageView() {
        super("Welcome");
    }

    public WelcomePageView(int fixedHourOfDay) {
        this();
        this.fixedHourOfDay = fixedHourOfDay;
    }

    @Override
    public void display() {
        super.display();
        updateGreeting();
        System.out.println(greeting);
        for (String action : actions) {
            System.out.println(action);
        }
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
        String newGreeting = String.format("Good %s, anonymous!", timeOfDay);
        greeting = newGreeting;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WelcomePageView)) return false;
        WelcomePageView other = (WelcomePageView) o;
        return Objects.equals(greeting, other.greeting)
            && Objects.deepEquals(actions, other.actions)
            && Objects.equals(fixedHourOfDay, other.fixedHourOfDay);
    }

    public String basedOnHour(int hour) {
        if (hour < 12) return "morning";
        if (hour < 19) return "afternoon";
        return "evening";
    }
}