package view.kit;

import app.App;

public class PageView extends View {
    public String pageTitle;

    public String greeting;

    private final String headerTemplate = "== %s :: %s ==";

    public PageView(String pageTitle, String greeting) {
        this.pageTitle = pageTitle;
        this.greeting = greeting;
    }

    @Override
    public void render() {
        System.out.println(String.format(headerTemplate, App.shared().getAppName(), pageTitle));
        System.out.println(greeting);
    }
}
