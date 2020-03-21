package com.g1t11.socialmagnet.view.page;

import java.util.Objects;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.view.component.*;

/**
 * {@link PageView} is the base of all screens and menus.
 * <code>PageView</code> clears the entire console before rendering any
 * content, giving the effect of an in-place view that can be used to render
 * static views.
 * @see View
 */
public class PageView {
    private String pageTitle;

    /**
     * A Component injected from the previous {@link Controller} which allows 
     * us to render content after the screen is cleared by {@link PageView}.
     */
    private CompoundComponent status = new CompoundComponent();

    private final String headerTemplate = Painter.paint("== Social Magnet :: %s ==", Painter.Color.BOLD);

    public PageView(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public void setStatus(Component status) {
        this.status.clear();
        this.status.add(status);
    }

    public void setStatus(String statusText) {
        setStatus(new TextComponent(statusText));
    }

    public CompoundComponent getStatus() {
        return status;
    }

    public void clearStatus() {
        status.clear();
    }

    /**
     * A system-independent method of clearing the console.
     */
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void display() {
        clearScreen();
        System.out.println(String.format(headerTemplate, pageTitle));
        if (status != null) {
            status.render();
            clearStatus();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PageView)) return false;
        PageView other = (PageView) o;
        return Objects.equals(pageTitle, other.pageTitle)
            && Objects.equals(status, other.status);
    }
}
