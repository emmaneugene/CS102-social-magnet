package com.g1t11.socialmagnet.view.page.socialmagnet;

import java.util.List;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.social.CommonFriend;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;

public class FriendsWallPageView extends WallPageView {
    User me;
    Farmer friend;
    List<User> friendFriends;

    public FriendsWallPageView(User me, Farmer friend,
            List<User> friendFriends) {
        super(friend, String.format("%s's Wall", friend.getUsername()));
        this.me = me;
        this.friend = friend;
        this.friendFriends = friendFriends;
    }

    @Override
    public void display() {
        appendGreeting();
        super.display();
        System.out.printf(Painter.paintf(
                "[{%s}]'s friends\n", Color.BLUE), friend.getUsername());
        int index = 1;
        for (User friend : friendFriends) {
            System.out.printf("%d. %s %s\n",
                index++,
                friend.getFullname(),
                (friend instanceof CommonFriend ? "(Common Friend)" : "")
            );
        }
        System.out.println();
    }

    private void appendGreeting() {
        appendStatus(String.format("Welcome, %s!\n", me.getFullname()));
    }

    @Override
    public void showMainPrompt() {
        showPrompt(Painter.paintf(
                "[[{M}]]ain | [[{T}]]hread | [[{P}]]ost", Color.YELLOW));
        setInputColor(Color.YELLOW);
    }
}
