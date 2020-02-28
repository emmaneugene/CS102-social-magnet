import app.App;
import controller.WelcomePageController;

public class AppRunner {
    public static void main(String[] args) {
        App.shared().setAppName("Social Magnet");
        App.shared().setFirstController(new WelcomePageController());
        App.shared().run();
    }
} 
