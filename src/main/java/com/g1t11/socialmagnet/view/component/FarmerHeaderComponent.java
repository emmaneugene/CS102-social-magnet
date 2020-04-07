package com.g1t11.socialmagnet.view.component;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;

public class FarmerHeaderComponent implements Component {
    private Farmer me;

    private static final String template = Painter.paintf(
        String.join(System.lineSeparator(),
            "Welcome, %s!",
            "Title: [{%s}]             Gold: [{%d gold}]",
            ""
        ),
        Color.BRIGHT_CYAN,
        Color.BRIGHT_YELLOW
    );

    public void setFarmer(Farmer me) {
        this.me = me;
    }

    @Override
    public void render() {
        System.out.printf(template, me.getFullname(),
                me.getRank().value, me.getWealth());
    }
}
