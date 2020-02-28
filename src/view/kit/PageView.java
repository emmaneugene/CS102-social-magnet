package view.kit;

import app.App;

/**
  * {@link PageView} is the base of all screens and menus.
  * <code>PageView</code> clears the entire console before rendering any
  * content, giving the effect of an in-place view that can be used to render
  * static views.
  * @see View
  */
public class PageView extends View {
    public String pageTitle;

    private final String headerTemplate = "== %s :: %s ==";

    public PageView(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    @Override
    public void render() {
        clearScreen();
        renderPre();
        renderMain();
        renderPost();
    }

    @Override
    public void renderMain() {
        System.out.println(String.format(headerTemplate, App.shared().getAppName(), pageTitle));
    }
}
