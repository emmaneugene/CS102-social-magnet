package com.g1t11.socialmagnet.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.TestApp;
import com.g1t11.socialmagnet.model.kit.NumberedAction;
import com.g1t11.socialmagnet.view.kit.TextView;

public class TestWelcomePageView extends TestApp {
    private final List<NumberedAction> actions = new ArrayList<>(List.of(
        new NumberedAction("Register", "1"),
        new NumberedAction("Login", "2"),
        new NumberedAction("Exit", "3")
    ));

    WelcomePageView view = null;

    @BeforeEach
    public void init() {
        view = new WelcomePageView(actions);
    }

    @Test
    @Order(1)
    public void testRender() {
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
    @Order(2)
    public void testWithStatus() {
        view.setStatus(new TextView("Please enter a choice between 1 & 3!"));

        view.render();

        String expected = "\033[H\033[2J";
        expected += String.join("\n",
            "== Social Magnet :: Welcome ==",
            "Please enter a choice between 1 & 3!",
            "Good morning, anonymous!",
            "1. Register",
            "2. Login",
            "3. Exit",
            "Enter your choice > "
        );

        Assertions.assertEquals(expected, getOutput());
    }
}
