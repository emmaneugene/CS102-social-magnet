package com.g1t11.socialmagnet.view.kit;

import java.util.List;
import java.util.Objects;

/**
 * A <code>View</code> component that stores and renders a list of 
 * <code>View</code>.
 * 
 * @see View
 */
public class ListView extends View {
    List<? extends View> items;

    public ListView(List<? extends View> items) {
        super();
        this.items = items;
    }

    @Override
    public void render() {
        for (View item : items) {
            item.render();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ListView)) return false;
        ListView other = (ListView) o;
        return Objects.deepEquals(items, other.items);
    }
}
