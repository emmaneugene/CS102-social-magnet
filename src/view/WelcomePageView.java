package view;

import java.util.List;

import model.NumberedAction;
import view.kit.*;

public class WelcomePageView extends PageView {
    public WelcomePageView(String greeting, List<NumberedAction> actions) {
        super("Welcome", greeting);
        addSubview(new ListView(actions));
        addSubview(new PromptView());
    }

	@Override
	public void render() {
        super.render();
        for (View subview : subviews) {
            subview.render();
        }
	}
}
