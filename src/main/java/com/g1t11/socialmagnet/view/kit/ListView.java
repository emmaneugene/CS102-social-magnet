package com.g1t11.socialmagnet.view.kit;

import java.util.List;

import com.g1t11.socialmagnet.view.kit.ListItemDataSource;

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
}
