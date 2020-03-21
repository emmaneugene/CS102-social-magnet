package com.g1t11.socialmagnet.view.component;

import java.util.ArrayList;
import java.util.List;

public class CompoundComponent implements Component {
    List<Component> components = new ArrayList<>();

    @Override
    public void render() {
        for (Component comp : components) {
            comp.render();
        }
    }

    public void add(Component comp) {
        components.add(comp);
    }

    public void clear() {
        components.clear();
    }
}