package view.kit;

import app.App;

public class PageView extends View {
    public String pageTitle;

    private final String headerTemplate = "== %s :: %s ==";

    public PageView(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    @Override
    public void render() {
        System.out.println(String.format(headerTemplate, App.shared().getAppName(), pageTitle));
    }
}
