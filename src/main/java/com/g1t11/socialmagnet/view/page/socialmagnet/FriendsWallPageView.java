package com.g1t11.socialmagnet.view.page.socialmagnet;

import java.util.List;

import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.social.Friend;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;

/**
 * This is a page view for Friend's Wall page.
 */
public class FriendsWallPageView extends WallPageView {
    private User me;

    private Farmer friend;

    private List<Friend> friendFriends;

    /**
     * Creates a Friend's Wall page with specified friend's friend as well as
     * the common friends with the user.
     * @param me The user.
     * @param friend Ther user's friend.
     * @param friendFriends The list of common friends.
     */
    public FriendsWallPageView(User me, Farmer friend,
            List<Friend> friendFriends) {
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
        for (Friend friend : friendFriends) {
            System.out.printf("%d. %s %s\n",
                index++,
                friend.getFullname(),
                (friend.isMutual() ? "(Common Friend)" : "")
            );
        }
        System.out.println();
    }
    
    /**
     * Append the status with the appropriate greeting based on full name of 
     * user.
     */
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
