package view.kit;

import java.util.ArrayList;
import java.util.List;

public abstract class View {
    public List<View> subviews = new ArrayList<>();

    public void addSubview(View view) {
        subviews.add(view);
    }

    public abstract void render();
}
