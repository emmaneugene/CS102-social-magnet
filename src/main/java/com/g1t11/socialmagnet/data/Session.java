package com.g1t11.socialmagnet.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.g1t11.socialmagnet.model.social.User;

/**
 * Handles user authentication and session management.
 */
public class Session extends DAO {
    private UserDAO userDAO;

    /**
     * Currently logged-in user
     */
    private User user = null;

    public Session(Database db) {
        super(db);
        userDAO = new UserDAO(db);
    }

    /**
     * Get the currently logged-in user.
     */
    public User currentUser() {
        return user;
    }

    /**
     * Verify login information against the database.
     */
    public boolean login(String username, String password) {
        if (!credentialsValid(username, password))
            return false;
        user = userDAO.getUser(username);
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

        String queryString = "CALL verify_credentials(?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            rs = stmt.executeQuery();
            rs.next();
            return rs.getBoolean("is_valid");
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new ConnectionFailureException();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }
    }

    public boolean userExists(String username) {
        ResultSet rs = null;

        String queryString = "CALL user_exists(?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            rs.next();
            return rs.getBoolean("user_exists");
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new ConnectionFailureException();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }
    }

    /**
     * Adds a user with the given parameters to the database.
     *
     * If a user with the same username already exists, then return false.
     * @return True if the user was added successfullly.
     */
    public boolean addUser(String username, String fullname, String pwd) {
        String queryString = "CALL add_user(?, ?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, username);
            stmt.setString(2, fullname);
            stmt.setString(3, pwd);

            stmt.execute();
        } catch (SQLException e) {
            // 1062 represents a duplicate primary key entry.
            if (e.getErrorCode() == 1062) return false;
            System.err.println("SQLException: " + e.getMessage());
            throw new ConnectionFailureException();
        }
        return true;
    }
}
