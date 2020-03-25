package com.g1t11.socialmagnet.view.page.cityfarmers;

import java.util.List;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.util.Painter;

public class CityFarmersMainMenuPageView extends CityFarmersPageView {
    private final List<String> actions = List.of(
        Painter.paintf("[{1.}] My Farmland", Painter.Color.YELLOW),
        Painter.paintf("[{2.}] My Store", Painter.Color.YELLOW),
        Painter.paintf("[{3.}] My Inventory", Painter.Color.YELLOW),
        Painter.paintf("[{4.}] Visit Friend", Painter.Color.YELLOW),
        Painter.paintf("[{5.}] Send Gift", Painter.Color.YELLOW)
    );

    public CityFarmersMainMenuPageView(Farmer me) {
        super(me);
    }

    @Override
    public void display() {
        super.display();
        for (String action : actions) {
            System.out.println(action);
        }
    }
}