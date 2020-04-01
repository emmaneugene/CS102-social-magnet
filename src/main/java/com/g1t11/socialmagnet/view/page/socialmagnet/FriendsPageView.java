package com.g1t11.socialmagnet.view.page.socialmagnet;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.PageView;

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
            System.out.printf(Painter.paintf("[{%d}]. [{%s}]\n", Color.YELLOW, Color.BLUE), index++, friend.getUsername());
        }

        System.out.println();
        System.out.println("My Requests:");
        for (String requestUsername : requestUsernames) {
            System.out.printf(Painter.paintf("[{%d}]. [{%s}]\n", Color.YELLOW, Color.BLUE), index++, requestUsername);
        }

        System.out.println();
    }

    @Override
    public void showMainPrompt() {
        showPrompt(Painter.paintf(
                "[[{M}]]ain | [[{U}]]nfriend | re[[{Q}]]uest"
                        + " | [[{A}]]ccept | [[{R}]]eject | [[{V}]]iew",
                Color.YELLOW));
        setInputColor(Color.YELLOW);
    }

    public void showRequestUsernamePrompt() {
        showPrompt("Enter the username");
        setInputColor(Color.BLUE);
    }
}
