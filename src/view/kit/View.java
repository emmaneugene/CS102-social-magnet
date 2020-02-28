package view.kit;

import java.util.ArrayList;
import java.util.List;

public abstract class View {
    public List<View> preViews = new ArrayList<>();
    public List<View> postViews = new ArrayList<>();

    public void render() {
        renderPre();
        renderMain();
        renderPost();
    };

    public void renderPre() {
        for (View preView : preViews) {
            preView.render();
        }
        preViews.clear();
    }

    public void renderPost() {
        for (View postView : postViews) {
            postView.render();
        }
        postViews.clear();
    }

    public abstract void renderMain();

    public void addPreView(View view) {
        preViews.add(view);
    }

    public void addPostView(View view) {
        postViews.add(view);
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
    }
}
