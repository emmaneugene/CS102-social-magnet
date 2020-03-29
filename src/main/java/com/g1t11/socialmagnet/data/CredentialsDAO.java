package com.g1t11.socialmagnet.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.g1t11.socialmagnet.model.social.User;

/**
 * Handles user authentication and session management.
 */
public class CredentialsDAO extends DAO {
    public CredentialsDAO(Database db) {
        super(db);
    }

    /**
     * Verify login information against the database.
     */
    public User login(String username, String password) {
        ResultSet rs = null;
        User user = null;

        String queryString = "CALL login(?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
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

    public void register(String username, String fullname, String password) {
        String queryString = "CALL register(?, ?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, username);
            stmt.setString(2, fullname);
            stmt.setString(3, password);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }

    public boolean userExists(String username) {
        ResultSet rs = null;
        boolean exists = true;

        String queryString = "CALL user_exists(?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
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