package com.g1t11.socialmagnet.view.component;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.util.Painter;

public class FarmerHeaderComponent implements Component {
    Farmer me;

    private static final String template = Painter.paintf(
        String.join(System.lineSeparator(),
            "Welcome, %s!",
            "Title: %s             Gold: %d gold",
            ""
        ),
        Painter.Color.BLUE
    );

    public void setFarmer(Farmer me) {
        this.me = me;
    }

    @Override
    public void render() {
        System.out.printf(template, me.getFullname(), me.getRank().value, me.getWealth());
    }
}
