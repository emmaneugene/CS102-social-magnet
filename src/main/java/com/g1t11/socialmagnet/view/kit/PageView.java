package com.g1t11.socialmagnet.view.kit;

import com.g1t11.socialmagnet.App;

/**
  * {@link PageView} is the base of all screens and menus.
  * <code>PageView</code> clears the entire console before rendering any
  * content, giving the effect of an in-place view that can be used to render
  * static views.
  * @see View
  */
public class PageView extends View {
  private String pageTitle;

  /**
    * A View injected from the previous {@link Controller} which allows us to
    * render content after the screen is cleared by {@link PageView}.
    */
  private View status = null;

  private final String headerTemplate = "== %s :: %s ==";

  public PageView(String pageTitle) {
    this.pageTitle = pageTitle;
  }

  public String getPageTitle() {
    return pageTitle;
  }

  public void setPageTitle(String pageTitle) {
    this.pageTitle = pageTitle;
  }

  public void setStatus(View status) {
    this.status = status;
  }

  public void clearStatus() {
    this.status = null;
  }

  @Override
  public void render() {
    clearScreen();
    System.out.println(String.format(headerTemplate, App.shared().getAppName(), pageTitle));
    if (status != null)
      status.render();
  }
}
