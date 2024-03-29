package com.g1t11.socialmagnetservice.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.g1t11.socialmagnetservice.data.DatabaseException.SQLErrorCode;
import com.g1t11.socialmagnetservice.model.social.User;

/**
 * Handles user authentication and session management.
 */
public class CredentialsDAO extends DAO {
    /**
     * Get the logged-in user if login is successful. Otherwise, return null.
     *
     * @param username The inputted username.
     * @param password The inputted password.
     * @return A user model if the login is successful, else null.
     */
    public static User login(String username, String password) {
        ResultSet rs = null;
        User user = null;

        String queryString = "CALL login(?, ?)";

        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            rs = stmt.executeQuery();

            if (!rs.next()) return null;
            user = new User(rs.getString("username"), rs.getString("fullname"));
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }
        return user;
    }

    /**
     * Register a new user on the database.
     *
     * @param username The new username.
     * @param fullname The new fullname.
     * @param password The new password.
     */
    public static void register(String username, String fullname, String password) {
        String queryString = "CALL register(?, ?, ?)";

        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setString(1, username);
            stmt.setString(2, fullname);
            stmt.setString(3, password);

            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == SQLErrorCode.DUPLICATE_KEY.value) {
                throw new DatabaseException(
                        SQLErrorCode.REGISTER_EXISTING_USER);
            }
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }

    /**
     * Check if the user account with the name username exists.
     *
     * @param username The user to check.
     * @return The user exists in the database.
     */
    public static boolean userExists(String username) {
        ResultSet rs = null;
        boolean exists = true;

        String queryString = "CALL user_exists(?)";

        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setString(1, username);

            rs = stmt.executeQuery();

            rs.next();
            exists = rs.getBoolean("user_exists");
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }
        return exists;
    }
}
