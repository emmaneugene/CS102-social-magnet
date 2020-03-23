package com.g1t11.socialmagnet.view.page;

import java.util.List;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.view.component.FarmerHeaderComponent;

public class CityFarmersPageView extends PageView {
    FarmerHeaderComponent headerComp;

    private final List<String> actions = List.of(
        Painter.paintf("[{1.}] My Farmland", Painter.Color.YELLOW),
        Painter.paintf("[{2.}] My Store", Painter.Color.YELLOW),
        Painter.paintf("[{3.}] My Inventory", Painter.Color.YELLOW),
        Painter.paintf("[{4.}] Visit Friend", Painter.Color.YELLOW),
        Painter.paintf("[{5.}] Send Gift", Painter.Color.YELLOW)
    );

    public CityFarmersPageView(Farmer me) {
        super("City Farmers");
        headerComp = new FarmerHeaderComponent(me);
    }

    @Override
    public void display() {
        super.display();
        headerComp.render();
        System.out.println();

        for (String action : actions) {
            System.out.println(action);
        }
    }

    public CityFarmersPageView() {
        super("City Farmers");
    }
}