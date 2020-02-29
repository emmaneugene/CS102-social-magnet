package view;

import view.kit.*;

/* SAMPLE

== Social Magnet :: Registration ==
Enter your username > kenny
Enter your Full name> Kenny LEE
Enter your password > secret
Confirm your password > secret
kenny, your account is successfully created!

*/

public class RegisterPageView extends PageView {
	public RegisterPageView() {
		super("Registration");
	}

	@Override
	public void renderMain() {
        super.renderMain();
		System.out.println("Register?");
	}
}
