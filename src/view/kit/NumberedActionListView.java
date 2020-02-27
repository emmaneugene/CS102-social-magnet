package view.kit;

import java.util.List;

import model.NumberedAction;

public class NumberedActionListView extends View {
    List<NumberedAction> dataSource;

    public NumberedActionListView(List<NumberedAction> dataSource) {
        super();
        this.dataSource = dataSource;
    }

    @Override
    public void render() {
        for (NumberedAction action : dataSource) {
            NumberedActionItemView view = new NumberedActionItemView(action);
            view.render();
        }
    }
}
