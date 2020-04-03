package com.g1t11.socialmagnet.view.component;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.TextUtils;
import com.g1t11.socialmagnet.util.Painter.Color;

/**
 * This is a render component for header of Friend's Farmer.
 */
public class FriendFarmerHeaderComponent implements Component {
    Farmer friend;

    private static final String template = Painter.paintf(
        String.join(System.lineSeparator(),
            "Name: %s",
            "Title: [{%s}]",
            "Gold: [{%s}]",
            ""
        ),
        Color.BRIGHT_CYAN,
        Color.BRIGHT_YELLOW
    );

    /**
     * Create a Friend's Farmer component with specified friend.
     * @param friend The friend.
     */
    public FriendFarmerHeaderComponent(Farmer friend) {
        this.friend = friend;
    }

    /**
     * A method used to render out header for Friend's Farmer.
     */
    @Override
    public void render() {
        System.out.printf(template,
                friend.getFullname(),
                friend.getRank().value,
                TextUtils.prettyNumber(friend.getWealth()));
    }
}
