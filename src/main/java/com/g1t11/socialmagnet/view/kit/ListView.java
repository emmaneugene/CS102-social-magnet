package com.g1t11.socialmagnet.view.kit;

import java.util.List;
import java.util.Objects;

/**
 * A {@link View} component that renders a list of items.
 * Items are rendered by implementing {@link ListItemDataSource}.
 * @see ListItemDataSource
 */
public class ListView extends View {
    List<? extends ListItemDataSource> dataSource;

    public ListView(List<? extends ListItemDataSource> dataSource) {
        super();
        this.dataSource = dataSource;
    }

    @Override
    public void render() {
        for (ListItemDataSource ds : dataSource) {
            ds.render();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ListView)) return false;
        ListView other = (ListView) o;
        return Objects.deepEquals(dataSource, other.dataSource);
    }

    @Override
    public String toString() {
        String result = "";
        for (ListItemDataSource ds : dataSource) {
            result += ds.toString() + "\n";
        }
        return result;
    }
}
