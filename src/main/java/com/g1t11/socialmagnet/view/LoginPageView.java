package com.g1t11.socialmagnet.view;

import com.g1t11.socialmagnet.view.kit.*;

/* SAMPLE

== Social Magnet :: Login ==
Enter your username > kenny
Enter your password > secret

*/

public class LoginPageView extends PageView {
	public LoginPageView() {
		super("Login");
	}

	@Override
	public void render() {
        super.render();
		System.out.println("Login?");
	}
}
