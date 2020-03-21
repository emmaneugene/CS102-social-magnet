package com.g1t11.socialmagnet.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.RequestExistingFriendException;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.model.social.UserNotFoundException;

public class UserDAO extends DAO {
    public UserDAO(Connection conn) {
        super(conn);
    }

    public User getUser(String username) {
        ResultSet rs = null;
        User u = null;

        String queryString = "CALL get_user(?)";

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            if (!rs.next()) throw new UserNotFoundException();

            String fullname = rs.getString("fullname");

            u = new User(username, fullname);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return u;
    }

    /**
     * Only loads usernames and full names of friends.
     * 
     * @param user The user whose friends to get
     */
    public List<User> getFriends(User user) {
        ResultSet rs = null;
        List<User> friends = new ArrayList<>();

        String queryString = "CALL get_friends(?)";

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setString(1, user.getUsername());

            rs = stmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String fullname = rs.getString("fullname");

                User u = new User(username, fullname);
                friends.add(u);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return friends;
    }

    public List<String> getRequestUsernames(User user) {
        ResultSet rs = null;
        List<String> requestUsernames = new ArrayList<>();

        String queryString = "CALL get_requests(?)";

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setString(1, user.getUsername());

            rs = stmt.executeQuery();
            while (rs.next()) {
                requestUsernames.add(rs.getString("sender"));
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return requestUsernames;
    }

    public void makeRequest(User sender, String recipient) {
        String queryString = "CALL make_request(?, ?)";

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setString(1, sender.getUsername());
            stmt.setString(2, recipient);

            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("Cannot request existing friend")) {
                throw new RequestExistingFriendException();
            } else if (e.getMessage().contains("User not found")) {
                throw new UserNotFoundException();
            }
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    public void acceptRequest(String sender, User recipient) {
        String queryString = "CALL accept_request(?, ?)";

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setString(1, sender);
            stmt.setString(2, recipient.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    public void rejectRequest(String sender, User recipient) {
        String queryString = "CALL reject_request(?, ?)";

        try ( PreparedStatement stmt = getConnection().prepareStatement(queryString); ) {
            stmt.setString(1, sender);
            stmt.setString(2, recipient.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }
}
