package com.g1t11.socialmagnet.data;

import java.sql.Connection;

import com.g1t11.socialmagnet.model.social.User;

/**
 * Handles user authentication and session management.
 */
public class Session {
    UserDAO userDao = null;

    /**
     * Currently logged-in user
     */
    private User user = null;

    public Session(UserDAO userDao) {
        this.userDao = userDao;
    }

    /**
     * Get the currently logged-in user's name.
     * If no user is logged in, return "anonymous".
     */
    public String getUsername() {
        if (user == null)
            return "anonymous";
        return user.getUsername();
    }

    /**
     * Verify login information against the database.
     */
    public boolean login(String username, String password) {
        if (userDao.checkCredentials(username, password)) {
            user = userDao.getUser(username);
            return true;
        }
        return false;
    }
}