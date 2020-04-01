package com.g1t11.socialmagnet.view.page;

import java.util.Objects;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;

/**
 * {@link PageView} is the base of all screens and menus.
 * <code>PageView</code> clears the entire console before rendering any
 * content, giving the effect of an in-place view that can be used to render
 * static views.
 * @see View
 */
public abstract class PageView {
    private String[] pageTitles;

    /**
     * A Component injected from the previous {@link Controller} which allows
     * us to render content after the screen is cleared by {@link PageView}.
     */
    private String status = null;

    public static final String APP_TITLE = "Social Magnet";
    public static final String PROMPT_INDICATOR = ">";
    public static final String DEFAULT_PROMPT = "Enter your choice";

    public PageView(String ...pageTitles) {
        setPageTitle(pageTitles);
    }

    public void setPageTitle(String ...pageTitles) {
        this.pageTitles = pageTitles;
    }

    /**
     * Sets the status of the PageView.
     * @param text The status message to set.
     */
    public void setStatus(String text) {
        status = text + "\n";
    }

    /**
     * Append a status message to the current status message, or create a new
     * one if the current status message is null.
     * @param text The status message to add.
     */
    public void appendStatus(String text) {
        if (status == null) {
            setStatus(text);
            return;
        }
        status += text + "\n";
    }

    public void clearStatus() {
        status = null;
    }

    /**
     * A system-independent method of clearing the console and resetting all
     * formatting on the screen.
     */
    public void clearScreen() {
        System.out.print(Color.RESET);
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void display() {
        clearScreen();
        displayHeader();
        if (status != null) {
            System.out.print(status);
            clearStatus();
        }
    }

    private void displayHeader() {
        StringBuilder headerBuilder = new StringBuilder("== ");
        headerBuilder.append(APP_TITLE);
        for (String title : pageTitles) {
            headerBuilder.append(" :: ");
            headerBuilder.append(title);
        }
        headerBuilder.append(" ==");
        System.out.println(Painter.paint(
                headerBuilder.toString(), Color.BOLD));
    }

    protected final void showPrompt(String message) {
        System.out.printf("%s %s ", message, PROMPT_INDICATOR);
    }

    public void showMainPrompt() {
        System.out.printf("%s %s ", DEFAULT_PROMPT, PROMPT_INDICATOR);
        setInputColor(Color.YELLOW);
    }

    protected final void setInputColor(Color color) {
        System.out.print(color);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PageView)) return false;
        PageView other = (PageView) o;
        return Objects.deepEquals(pageTitles, other.pageTitles)
            && Objects.equals(status, other.status);
    }
}
