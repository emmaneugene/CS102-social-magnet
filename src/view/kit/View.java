package view.kit;

import java.util.ArrayList;
import java.util.List;

public abstract class View {
    /**
     * A list of View objects to be rendered before the main body.
     * Normally used to render content that has to be injected from the
     * previous View on the navigation stack.
     * Especially useful for passing <code>View</code> objects to be rendered
     * in another {@link PageView} after the <code>PageView</code> clears the
     * console.
     * @see PageView
     */
    public List<View> preViews = new ArrayList<>();

    /**
     * A list of View objects to be rendered after the main body.
     */
    public List<View> postViews = new ArrayList<>();

    public void render() {
        renderPre();
        renderMain();
        renderPost();
    };

    /**
     * Render injected {@link View} objects and remove them from this instance.
     * If a <code>View</code> is repeatedly refreshed, its {@link #preViews}
     * could be repeatedly populated by the same <code>View</code>. Therefore,
     * <code>preViews</code> must be cleared after each frame.
     * @see #renderPost()
     */
    public void renderPre() {
        for (View preView : preViews) {
            preView.render();
        }
        preViews.clear();
    }

    /**
     * Render injected {@link View} objects and remove them from this instance.
     * @see #renderPre()
     */
    public void renderPost() {
        for (View postView : postViews) {
            postView.render();
        }
        postViews.clear();
    }

    /**
     * The main content of the {@link View} is rendered here.
     */
    public abstract void renderMain();

    /**
     * @see #preViews
     */
    public void addPreView(View view) {
        preViews.add(view);
    }

    /**
     * @see #postViews
     */
    public void addPostView(View view) {
        postViews.add(view);
    }

    /**
     * A system-independent method of clearing the console.
     */
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
