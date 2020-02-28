import data.AppData;

import controller.WelcomePageController;

public class App {
    public static void main(String[] args) {
        AppData.shared().setAppName("Social Magnet");
        AppData.shared().setFirstController(new WelcomePageController());
        AppData.shared().run();
    }
} 
