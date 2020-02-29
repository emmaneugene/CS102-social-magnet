package view.kit;

import java.util.ArrayList;
import java.util.List;

public abstract class View {
    /**
     * Render the main content of the {@link View}.
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
