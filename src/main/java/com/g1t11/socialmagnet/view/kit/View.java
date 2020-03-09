package com.g1t11.socialmagnet.view.kit;

public abstract class View {
    /**
     * Render the main content of the <code>View</code>.
     */
    public abstract void render();

    /**
     * A system-independent method of clearing the console.
     */
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
