package com.g1t11.socialmagnet.view.page.cityfarmers;

import java.util.List;

import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;

/**
 * This is a page view for City Farmers Main Menu page. It lists down the 
 * actions and prompt user for action.
 */
public class CityFarmersMainMenuPageView extends CityFarmersPageView {
    private final List<String> actions = List.of(
        Painter.paintf("[{1.}] My Farmland", Color.YELLOW),
        Painter.paintf("[{2.}] My Store", Color.YELLOW),
        Painter.paintf("[{3.}] My Inventory", Color.YELLOW),
        Painter.paintf("[{4.}] Visit Friend", Color.YELLOW),
        Painter.paintf("[{5.}] Send Gift", Color.YELLOW)
    );

    @Override
    public void display() {
        super.display();
        for (String action : actions) {
            System.out.println(action);
        }
    }

    @Override
    public void showMainPrompt() {
        showPrompt(Painter.paintf(
                "[[{M}]]ain | Enter your choice", Color.YELLOW));
    }
}
