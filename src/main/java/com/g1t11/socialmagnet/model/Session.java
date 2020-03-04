package com.g1t11.socialmagnet.model;

import java.sql.Connection;

import com.g1t11.socialmagnet.data.UserDAO;
import com.g1t11.socialmagnet.model.User;

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
     * @throws WrongPasswordException Wrong password given.
     * @throws UserNotFoundException User does not exist in the database.
     */
    public boolean login(String username, String password) {
        if (userDao.checkCredentials(username, password)) {
            user = userDao.getUser(username);
            return true;
        }
        return false;
    }
}
