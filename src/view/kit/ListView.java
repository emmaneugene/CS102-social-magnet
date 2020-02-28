package view.kit;

import java.util.List;

import view.kit.ItemViewDataSource;

public class ListView extends View {
    List<? extends ItemViewDataSource> dataSource;

    public ListView(List<? extends ItemViewDataSource> dataSource) {
        super();
        this.dataSource = dataSource;
    }

    @Override
    public void renderMain() {
        for (ItemViewDataSource ds : dataSource) {
            ds.render();
        }
    }
}
