package com.g1t11.socialmagnet.view.component;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;

/**
 * This is a render component for profile info in wall.
 */
public class WallProfileInfoComponent implements Component {
    Farmer me;

    private static final String template = Painter.paintf(
        String.join(System.lineSeparator(),
            "About [{%s}]",
            "Full Name: %s",
            "[{%s}] Farmer, [{%s}] richest",
            ""
        ),
        Color.BLUE, Color.BRIGHT_CYAN, Color.BRIGHT_YELLOW);

    /**
     * Creates a wall profile info component with specified farmer.
     * @param me The farmer.
     */
    public WallProfileInfoComponent(Farmer me) {
        this.me = me;
    }

    /**
     * A method used to render out the profile info in wall.
     */
    @Override
    public void render() {
        System.out.printf(template, me.getUsername(), me.getFullname(),
                me.getRank().value, prettyRank(me.getWealthRankAmongFriends()));
    }

    /**
     * A method to decide which ordinal indicator (st, nd, rd, th) to use.
     * @param i The number.
     * @return The correct ordinal indicator.
     */
    private String prettyRank(int i) {
        if (i % 10 == 1 && i % 100 != 11) {
            return i + "st";
        }
        if (i % 10 == 2 && i % 100 != 12) {
            return i + "nd";
        }
        if (i % 10 == 3 && i % 100 != 13) {
            return i + "rd";
        }
        return i + "th";
    }
}
