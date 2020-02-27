package view;

import java.util.List;

import model.NumberedAction;
import view.kit.*;

public class WelcomePageView extends PageView {
    List<NumberedAction> actions;

    public WelcomePageView(String title, String greeting, List<NumberedAction> actions) {
        super(title, greeting);
        this.actions = actions;

        addSubview(new NumberedActionListView(actions));
    }

	@Override
	public void render() {
        super.render();
        for (View subview : subviews) {
            subview.render();
        }
	}
}
