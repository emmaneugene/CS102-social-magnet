package view;

import java.util.List;
import java.util.Calendar;

import app.App;
import model.NumberedAction;
import view.kit.*;

public class WelcomePageView extends PageView {
    public WelcomePageView(List<NumberedAction> actions) {
        super("Welcome");
        addSubview(new TextView("Good morning, anonymous!"));
        addSubview(new ListView(actions));
        addSubview(new PromptView());
    }

	@Override
	public void render() {
        super.render();
        updateGreeting();
        for (View subview : subviews) {
            subview.render();
        }
	}

    private void updateGreeting() {
        String greeting = String.format("Good %s, %s", getTimeOfDay(), App.shared().getUsername());
        ((TextView) subviews.get(0)).setText(greeting);
    }

    private String getTimeOfDay() {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        if (hour < 12) return "morning";
        if (hour < 19) return "afternoon";
        return "evening";
    }
}
