package com.g1t11.socialmagnet;

import org.junit.BeforeClass;

public class TestApp {
    @BeforeClass
    public static void initApplication() {
        App.shared().setAppName("Social Magnet");
        App.shared().getDatabase().establishDefaultConnection();
    }
}
