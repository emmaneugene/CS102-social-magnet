package com.g1t11.socialmagnet.controller.socialmagnet;

import java.util.List;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.model.social.Friend;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.socialmagnet.FriendsWallPageView;

/**
 * This is the controller for Friends' Wall.
 */
public class FriendsWallController extends WallController {
    public FriendsWallController(Navigator nav, User me, User friend) {
        super(nav, me);
        farmerToDisplay = farmerLoadDAO.getFarmer(friend.getUsername());
        List<Friend> friendsOfFriend = userDAO.getFriendsOfFriendWithCommon(
                me.getUsername(),
                farmerToDisplay.getUsername());
        setView(new FriendsWallPageView(me, farmerToDisplay, friendsOfFriend));
    }

    @Override
    public void handleInput() {
        getView().showMainPrompt();

        String choice = input.nextLine();
        if (choice.length() == 0) {
            setStatus(Painter.paint(
                    "Please select a valid option.", Color.RED));
        } else if (choice.equals("M")) {
            nav.popTo(MainMenuController.class);
        } else if (choice.charAt(0) == 'T') {
            handleThread(choice);
        } else if (choice.equals("P")) {
            handlePost();
        } else {
            setStatus(Painter.paint(
                    "Please select a valid option.", Color.RED));
        }
    }
}
