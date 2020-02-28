package view;

import java.util.List;
import java.util.Calendar;

import app.App;
import model.NumberedAction;
import view.kit.*;

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

    public WelcomePageView(List<NumberedAction> actions) {
        super("Welcome");
        greetingView = new TextView("Good morning, anonymous!");
        actionsView  = new ListView(actions);
        promptView   = new PromptView();
    }

	@Override
	public void renderMain() {
        super.renderMain();
        updateGreeting();
        greetingView.render();
        actionsView.render();
        promptView.render();
	}

    /**
     * Update {@link #greetingView} based on the time of day. 
     */
    private void updateGreeting() {
        String greeting = String.format("Good %s, %s!", getTimeOfDay(), App.shared().getUsername());
        greetingView.setText(greeting);
    }

    private String getTimeOfDay() {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        if (hour < 12) return "morning";
        if (hour < 19) return "afternoon";
        return "evening";
    }
}
