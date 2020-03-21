package com.g1t11.socialmagnet.controller;

import java.util.List;

import com.g1t11.socialmagnet.data.UserDAO;
import com.g1t11.socialmagnet.model.social.RequestExistingFriendException;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.model.social.UserNotFoundException;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.PromptInput;
import com.g1t11.socialmagnet.view.page.FriendsPageView;

public class FriendsController extends Controller {
    private UserDAO userDAO = new UserDAO(connection());

    private User currentUser;

    private List<User> friends;

    private List<String> requestUsernames;

    public FriendsController(Navigation nav) {
        super(nav);
        currentUser = nav.session().currentUser();
        view = new FriendsPageView(currentUser);
    }

    @Override
    public void updateView() {
        friends = userDAO.getFriends(currentUser);
        requestUsernames = userDAO.getRequestUsernames(currentUser);
        ((FriendsPageView) view).setFriends(friends);
        ((FriendsPageView) view).setRequestUsernames(requestUsernames);
        view.display();
    }

    @Override
    public void handleInput() {
        PromptInput input = new PromptInput(Painter.paintf(
            "[[{M}]]ain | [[{U}]]nfriend | re[[{Q}]]uest | [[{A}]]ccept | [[{R}]]eject | [[{V}]]iew", 
            Painter.Color.YELLOW
        ));
        String choice = input.nextLine();
        if (choice.equals("M")) {
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
            view.setStatus(Painter.paint("Please select a valid option.", Painter.Color.RED));
        }
    }

    private void handleUnfriend(String choice) {
        try {
            int index = Integer.parseInt(choice.substring(1));
            if (index <= 0 || index > friends.size()) {
                view.setStatus(Painter.paint("Index out of range.", Painter.Color.RED));
                return;
            }
            String toUnfriend = friends.get(index - 1).getUsername();
            userDAO.unfriend(currentUser, toUnfriend);
            view.setStatus(Painter.paintf(
                String.format("[{Unfriended [{%s}]}]!", toUnfriend), Painter.Color.GREEN, Painter.Color.BLUE
            ));
        } catch (NumberFormatException e) {
            view.setStatus(Painter.paint("Use U<id> to select a friend.", Painter.Color.RED));
        }
    }

    private void handleRequest() {
        updateView();
        PromptInput input = new PromptInput("Enter the username");
        String requested = input.nextLine();
        try {
            userDAO.makeRequest(currentUser, requested);
            view.setStatus(Painter.paintf(
                String.format("[{Sent [{%s}] a friend request!}]", requested), Painter.Color.GREEN, Painter.Color.BLUE
            ));
        } catch (UserNotFoundException e) {
            view.setStatus(Painter.paintf(
                String.format("[{User [{%s}] not found.}]", requested), Painter.Color.RED, Painter.Color.BLUE
            ));
        } catch (RequestExistingFriendException e) {
            view.setStatus(Painter.paint("Cannot request existing friend.", Painter.Color.RED));
        }
    }

    private void handleAccept(String choice) {
        try {
            int index = Integer.parseInt(choice.substring(1));
            if (index <= 0 || index > friends.size() + requestUsernames.size()) {
                view.setStatus(Painter.paint("Index out of range.", Painter.Color.RED));
                return;
            }
            if (index <= friends.size()) {
                view.setStatus(Painter.paint("Cannot accept existing friend.", Painter.Color.RED));
                return;
            }
            String requestUsername = requestUsernames.get(index - friends.size() - 1);
            userDAO.acceptRequest(requestUsername, currentUser);
            view.setStatus(Painter.paintf(
                String.format("[{Accepted [{%s}]!}]", requestUsername), Painter.Color.GREEN, Painter.Color.BLUE
            ));
        } catch (NumberFormatException e) {
            view.setStatus(Painter.paint("Use A<id> to select a request.", Painter.Color.RED));
        }
    }

    private void handleReject(String choice) {
        try {
            int index = Integer.parseInt(choice.substring(1));
            if (index <= 0 || index > friends.size() + requestUsernames.size()) {
                view.setStatus(Painter.paint("Index out of range.", Painter.Color.RED));
                return;
            }
            if (index <= friends.size()) {
                view.setStatus(Painter.paint("Cannot reject existing friend.", Painter.Color.RED));
                return;
            }
            String requestUsername = requestUsernames.get(index - friends.size() - 1);
            userDAO.rejectRequest(requestUsername, currentUser);
            view.setStatus(Painter.paintf(
                String.format("[{Rejected [{%s}]!}]", requestUsername), Painter.Color.GREEN, Painter.Color.BLUE
            ));
        } catch (NumberFormatException e) {
            view.setStatus(Painter.paint("Use R<id> to select a request.", Painter.Color.RED));
        }
    }

    private void handleView(String choice) {
        try {
            int index = Integer.parseInt(choice.substring(1));
            if (index <= 0 || index > friends.size()) {
                view.setStatus(Painter.paint("Index out of range.", Painter.Color.RED));
                return;
            }
            nav.push(new FriendsWallController(nav, friends.get(index - 1)));
        } catch (NumberFormatException e) {
            view.setStatus(Painter.paint("Use V<id> to view a friend's page.", Painter.Color.RED));
        }
    }
}