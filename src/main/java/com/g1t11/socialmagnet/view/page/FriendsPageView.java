package com.g1t11.socialmagnet.view.page;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;

public class FriendsPageView extends PageView {
    private User currentUser;

    private List<User> friends = new ArrayList<>();

    private List<String> requestUsernames = new ArrayList<>();

    public FriendsPageView(User currentUser) {
        super("My Friends");
        this.currentUser = currentUser;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public void setRequestUsernames(List<String> requestUsernames) {
        this.requestUsernames = requestUsernames;
    }

    @Override
    public void display() {
        super.display();
        System.out.printf("Welcome, %s!\n", currentUser.getFullname());

        System.out.println();
        System.out.println("My Friends:");
        int index = 1;
        for (User friend : friends) {
            System.out.printf(Painter.paintf("[{%d}]. [{%s}]\n", Painter.Color.YELLOW, Painter.Color.BLUE), index++, friend.getUsername());
        }

        System.out.println();
        System.out.println("My Requests:");
        for (String requestUsername : requestUsernames) {
            System.out.printf(Painter.paintf("[{%d}]. [{%s}]\n", Painter.Color.YELLOW, Painter.Color.BLUE), index++, requestUsername);
        }
    }
}