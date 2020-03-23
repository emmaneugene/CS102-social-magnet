package com.g1t11.socialmagnet.view.component;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.util.Painter;

public class ProfileInfoComponent implements Component {
    Farmer me;

    private static final String template = Painter.paintf(
        String.join("\n",
            "About [{%s}]",
            "Full Name: %s",
            "%s Farmer, %s richest\n\n\n"
        ),
        Painter.Color.BLUE
    );

    public ProfileInfoComponent(Farmer me) {
        this.me = me;
    }

    @Override
    public void render() {
        System.out.printf(template, me.getUsername(), me.getFullname(), me.getRank(), prettyRank(me.getWealthRankAmongFriends()));
    }

    private String prettyRank(int i) {
        if (i % 10 == 1 && i % 100 != 11) return i + "st";
        if (i % 10 == 2 && i % 100 != 12) return i + "nd";
        if (i % 10 == 3 && i % 100 != 13) return i + "rd";
        return i + "th";
    }
}