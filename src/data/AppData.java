package data;

public class AppData {
    private static AppData sharedInstance = null;

    private String appName;

    private AppData() {}

    public static AppData shared() {
        if (sharedInstance == null)
            sharedInstance = new AppData();
        return sharedInstance;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
