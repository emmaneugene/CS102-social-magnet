package view;

import java.util.List;

import model.NumberedAction;
import view.kit.*;

public class WelcomePageView extends PageView {
    public WelcomePageView(String title, String greeting, List<NumberedAction> actions) {
        super(title, greeting);
        addSubview(new ListView(actions));
    }

	@Override
	public void render() {
        super.render();
        for (View subview : subviews) {
            subview.render();
        }
	}
}
