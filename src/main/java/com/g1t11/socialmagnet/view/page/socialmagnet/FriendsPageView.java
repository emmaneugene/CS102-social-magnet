package com.g1t11.socialmagnet.view.page.socialmagnet;

import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.util.Painter;
import com.g1t11.socialmagnet.util.Painter.Color;
import com.g1t11.socialmagnet.view.page.PageView;

/**
 * This is a page view for Friends page.
 */
public class FriendsPageView extends PageView {
    private User currentUser;

    private List<User> friends = new ArrayList<>();

    private List<String> requestUsernames = new ArrayList<>();

    /**
     * Creates Friend's page view with current user.
     * @param currentUser The current user that is using the app.
     */
    public FriendsPageView(User currentUser) {
        super("My Friends");
        this.currentUser = currentUser;
    }

    /**
     * Sets the list of friends.
     * @param friends The list of friends.
     */
    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    /**
     * Sets the list of usernames of friend requests.
     * @param requestUsernames The list of usernames of friend requests.
     */
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
            System.out.printf(Painter.paintf("[{%d}]. [{%s}]\n",
                    Color.YELLOW, Color.BLUE), index++, friend.getUsername());
        }

        System.out.println();
        System.out.println("My Requests:");
        for (String requestUsername : requestUsernames) {
            System.out.printf(Painter.paintf("[{%d}]. [{%s}]\n",
                    Color.YELLOW, Color.BLUE), index++, requestUsername);
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

    /**
     * A method to prompt for username.
     */
    public void showRequestUsernamePrompt() {
        showPrompt("Enter the username");
        setInputColor(Color.BLUE);
    }
}
