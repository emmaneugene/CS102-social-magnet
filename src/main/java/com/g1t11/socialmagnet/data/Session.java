package com.g1t11.socialmagnet.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.g1t11.socialmagnet.model.social.User;

/**
 * Handles user authentication and session management.
 */
public class Session {
    Connection conn;

    UserDAO userDao;

    /**
     * Currently logged-in user
     */
    private User user = null;

    public Session(Connection conn) {
        this.conn = conn;
        userDao = new UserDAO(conn);
    }

    /**
     * Get the currently logged-in user's name.
     * If no user is logged in, return "anonymous".
     */
    public User getUser() {
        return user;
    }

    /**
     * Verify login information against the database.
     */
    public boolean login(String username, String password) {
        if (!credentialsValid(username, password))
            return false;
        user = userDao.getUser(username);
        return true;
    }

    public boolean logout() {
        if (user == null)
            return false;
        user = null;
        return true;
    }

    public boolean register(String username, String fullName, String password) {
        return addUser(username, fullName, password);
    }

    public boolean credentialsValid(String username, String password) {
        ResultSet rs = null;

        String queryString = String.join(" ",
            "SELECT username",
            "FROM user",
            "WHERE username = ? AND pwd = ?"
        );

        try ( PreparedStatement stmt = conn.prepareStatement(queryString); ) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return false;
    }

    public boolean userExists(String username) {
        ResultSet rs = null;

        String queryString = String.join(" ",
            "SELECT username",
            "FROM user",
            "WHERE username = ?"
        );

        try ( PreparedStatement stmt = conn.prepareStatement(queryString); ) {
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }
        return false;
    }

    /**
     * Adds a user with the given parameters to the database.
     *
     * If a user with the same username already exists, then return false.
     * @return True if the user was added successfullly.
     */
    public boolean addUser(String username, String fullname, String pwd) {
        ResultSet rs = null;

        String queryString = String.join(" ",
            "INSERT INTO user (username, fullname, pwd) VALUES",
            "(?, ?, ?)"
        );

        try ( PreparedStatement stmt = conn.prepareStatement(queryString); ) {
            stmt.setString(1, username);
            stmt.setString(2, fullname);
            stmt.setString(3, pwd);

            stmt.execute();
        } catch (SQLException e) {
            // 1062 represents a duplicate primary key entry.
            if (e.getErrorCode() == 1062) return false;

            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return true;
    }
}
