package com.g1t11.socialmagnet.controller.socialmagnet;

import java.util.List;

import com.g1t11.socialmagnet.controller.Navigator;
import com.g1t11.socialmagnet.data.ServerException;
import com.g1t11.socialmagnet.data.ServerException.SQLErrorCode;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.socialmagnet.FriendsPageView;

public class FriendsController extends SocialMagnetController {
    private List<User> friends;

    private List<String> requestUsernames;

    public FriendsController(Navigator nav, User me) {
        super(nav, me);
        setView(new FriendsPageView(me));
    }

    @Override
    public FriendsPageView getView() {
        return (FriendsPageView) super.getView();
    }

    @Override
    public void updateView() {
        friends = userDAO.getFriends(me.getUsername());
        requestUsernames = userDAO.getRequestUsernames(me.getUsername());
        getView().setFriends(friends);
        getView().setRequestUsernames(requestUsernames);
    }

    @Override
    public void handleInput() {
        getView().showMainPrompt();

        String choice = input.nextLine();
        if (choice.length() == 0) {
            setStatus(Painter.paint(
                    "Please select a valid option.", Color.RED));
        } else if (choice.equals("M")) {
            nav.pop();
        } else if (choice.charAt(0) == 'U') {
            handleUnfriend(choice);
        } else if (choice.equals("Q")) {
            handleRequest();
        } else if (choice.charAt(0) == 'A') {
            handleAccept(choice);
        } else if (choice.charAt(0) == 'R') {
            handleReject(choice);
        } else if (choice.charAt(0) == 'V') {
            handleView(choice);
        } else {
            setStatus(Painter.paint(
                    "Please select a valid option.", Color.RED));
        }
    }

    private void handleUnfriend(String choice) {
        try {
            int index = Integer.parseInt(choice.substring(1));

            if (index <= 0 || index > friends.size()) {
                setStatus(Painter.paint("Index out of range.", Color.RED));
                return;
            }

            String toUnfriend = friends.get(index - 1).getUsername();
            userDAO.unfriend(me.getUsername(), toUnfriend);

            setStatus(Painter.paintf(
                    String.format("[{Unfriended [{%s}]}]!", toUnfriend),
                    Color.GREEN,
                    Color.BLUE));
        } catch (NumberFormatException e) {
            setStatus(Painter.paint(
                    "Use U<id> to select a friend.", Color.RED));
        }
    }

    private void handleRequest() {
        // Clear the previous prompt by refreshing the view.
        getView().display();
        getView().showRequestUsernamePrompt();

        String requested = input.nextLine();
        try {
            userDAO.makeRequest(me.getUsername(), requested);

            setStatus(Painter.paintf(
                    String.format("[{Sent [{%s}] a friend request!}]",
                            requested),
                    Color.GREEN,
                    Color.BLUE));
        } catch (ServerException e) {
            int code = e.getCode();
            if (code == SQLErrorCode.USER_NOT_FOUND.value) {
                setStatus(Painter.paintf(
                        String.format("[{User [{%s}] not found.}]", requested),
                        Color.RED,
                        Color.BLUE));
            } else if (code == SQLErrorCode.REQUEST_EXISTING_FRIEND.value) {
                setStatus(Painter.paint(
                        "Cannot request existing friend.", Color.RED));
            } else if (code == SQLErrorCode.REQUEST_SELF.value) {
                setStatus(Painter.paint(
                        "Cannot request self.", Color.RED));
            }
        }
    }

    private void handleAccept(String choice) {
        try {
            int index = Integer.parseInt(choice.substring(1));

            if (index <= 0
                    || index > friends.size() + requestUsernames.size()) {
                setStatus(Painter.paint("Index out of range.", Color.RED));
                return;
            }

            if (index <= friends.size()) {
                setStatus(Painter.paint(
                        "Cannot accept existing friend.", Color.RED));
                return;
            }

            String requestUsername = requestUsernames.get(
                    index - friends.size() - 1);
            userDAO.acceptRequest(requestUsername, me.getUsername());

            setStatus(Painter.paintf(
                    String.format("[{Accepted [{%s}]!}]", requestUsername),
                    Color.GREEN, Color.BLUE));
        } catch (NumberFormatException e) {
            setStatus(Painter.paint(
                    "Use A<id> to select a request.", Color.RED));
        }
    }

    private void handleReject(String choice) {
        try {
            int index = Integer.parseInt(choice.substring(1));

            if (index <= 0
                    || index > friends.size() + requestUsernames.size()) {
                setStatus(Painter.paint("Index out of range.", Color.RED));
                return;
            }

            if (index <= friends.size()) {
                setStatus(Painter.paint(
                        "Cannot reject existing friend.", Color.RED));
                return;
            }

            String requestUsername = requestUsernames.get(
                    index - friends.size() - 1);
            userDAO.rejectRequest(requestUsername, me.getUsername());

            setStatus(Painter.paintf(
                    String.format("[{Rejected [{%s}]!}]", requestUsername),
                    Color.GREEN, Color.BLUE));
        } catch (NumberFormatException e) {
            setStatus(Painter.paint(
                    "Use R<id> to select a request.", Color.RED));
        }
    }

    private void handleView(String choice) {
        try {
            int index = Integer.parseInt(choice.substring(1));

            if (index <= 0 || index > friends.size()) {
                setStatus(Painter.paint("Index out of range.", Color.RED));
                return;
            }
            nav.push(new FriendsWallController(nav, me,
                    friends.get(index - 1)));
        } catch (NumberFormatException e) {
            setStatus(Painter.paint(
                    "Use V<id> to view a friend's page.", Color.RED));
        }
    }
}
