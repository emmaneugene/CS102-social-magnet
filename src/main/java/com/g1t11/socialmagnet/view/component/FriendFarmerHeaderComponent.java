package com.g1t11.socialmagnet.view.component;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.util.TextUtils;

public class FriendFarmerHeaderComponent implements Component {
    Farmer friend;

    private static final String template = String.join(System.lineSeparator(),
        "Name: %s",
        "Title: %s",
        "Gold: %s",
        ""
    );

    public FriendFarmerHeaderComponent(Farmer friend) {
        this.friend = friend;
    }

    @Override
    public void render() {
        System.out.printf(template,
            friend.getFullname(),
            friend.getRank().value,
            TextUtils.prettyNumber(friend.getWealth())
        );
    }
}