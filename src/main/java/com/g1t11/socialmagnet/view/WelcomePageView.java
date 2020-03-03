package com.g1t11.socialmagnet.view;

import java.util.List;
import java.util.Objects;
import java.util.Calendar;

import com.g1t11.socialmagnet.App;
import com.g1t11.socialmagnet.model.kit.NumberedAction;
import com.g1t11.socialmagnet.util.Greeting;
import com.g1t11.socialmagnet.view.kit.*;

/* SAMPLE

== Social Magnet :: Welcome ==
Good morning, anonymous!
1. Register
2. Login 
3. Exit
Enter your choice > 4
Please enter a choice between 1 & 3! 

*/

public class WelcomePageView extends PageView {
    TextView greetingView;

    ListView actionsView;

    PromptView promptView;

    Integer fixedHourOfDay = null;

    public WelcomePageView(List<NumberedAction> actions) {
        super("Welcome");
        greetingView = new TextView("Good morning, anonymous!");
        actionsView  = new ListView(actions);
        promptView   = new PromptView();
    }

    public WelcomePageView(List<NumberedAction> actions, int fixedHourOfDay) {
        this(actions);
        this.fixedHourOfDay = fixedHourOfDay;
    }

    @Override
    public void render() {
        super.render();
        updateGreeting();
        greetingView.render();
        actionsView.render();
        promptView.render();
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
        String greeting = String.format("Good %s, %s!", time, App.shared().getSession().getUsername());
        greetingView.setText(greeting);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WelcomePageView)) return false;
        WelcomePageView other = (WelcomePageView) o;
        return Objects.equals(greetingView, other.greetingView)
            && Objects.equals(actionsView, other.actionsView)
            && Objects.equals(promptView, other.promptView)
            && Objects.equals(fixedHourOfDay, other.fixedHourOfDay);
    }
}
