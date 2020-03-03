package com.g1t11.socialmagnet.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.kit.NumberedAction;
import com.g1t11.socialmagnet.AppTest;
import com.g1t11.socialmagnet.view.kit.TextView;

public class WelcomePageViewTest extends AppTest {
    private final List<NumberedAction> actions = new ArrayList<>(List.of(
        new NumberedAction("Register", "1"),
        new NumberedAction("Login", "2"),
        new NumberedAction("Exit", "3")
    ));

    WelcomePageView view = null;

    @Test
    public void testRenderMorning() {
        view = new WelcomePageView(actions, 8);

        view.render();

        String expected = "\033[H\033[2J";
        expected += String.join("\n",
            "== Social Magnet :: Welcome ==",
            "Good morning, anonymous!",
            "1. Register",
            "2. Login",
            "3. Exit",
            "Enter your choice > "
        );

        Assertions.assertEquals(expected, getOutput());
    }

    @Test
    public void testRenderAfternoon() {
        view = new WelcomePageView(actions, 13);

        view.render();

        String expected = "\033[H\033[2J";
        expected += String.join("\n",
            "== Social Magnet :: Welcome ==",
            "Good afternoon, anonymous!",
            "1. Register",
            "2. Login",
            "3. Exit",
            "Enter your choice > "
        );

        Assertions.assertEquals(expected, getOutput());
    }

    @Test
    public void testRenderEvening() {
        view = new WelcomePageView(actions, 20);

        view.render();

        String expected = "\033[H\033[2J";
        expected += String.join("\n",
            "== Social Magnet :: Welcome ==",
            "Good evening, anonymous!",
            "1. Register",
            "2. Login",
            "3. Exit",
            "Enter your choice > "
        );

        Assertions.assertEquals(expected, getOutput());
    }

    @Test
    public void testWithStatus() {
        view = new WelcomePageView(actions, 20);

        view.setStatus(new TextView("Please enter a choice between 1 & 3!"));

        view.render();

        String expected = "\033[H\033[2J";
        expected += String.join("\n",
            "== Social Magnet :: Welcome ==",
            "Please enter a choice between 1 & 3!",
            "Good evening, anonymous!",
            "1. Register",
            "2. Login",
            "3. Exit",
            "Enter your choice > "
        );

        Assertions.assertEquals(expected, getOutput());
    }
}
