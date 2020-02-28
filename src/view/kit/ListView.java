package view.kit;

import java.util.List;

import view.kit.ListItemDataSource;

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
    public void renderMain() {
        for (ListItemDataSource ds : dataSource) {
            ds.render();
        }
    }
}
