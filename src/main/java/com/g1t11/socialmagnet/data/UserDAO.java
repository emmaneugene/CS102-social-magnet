package com.g1t11.socialmagnet.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnet.model.social.CommonFriend;
import com.g1t11.socialmagnet.model.social.RequestExistingFriendException;
import com.g1t11.socialmagnet.model.social.User;
import com.g1t11.socialmagnet.model.social.UserNotFoundException;

public class UserDAO extends DAO {
    public UserDAO(Database db) {
        super(db);
    }

    public User getUser(String username) {
        ResultSet rs = null;
        User u = null;

        String queryString = "CALL get_user(?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            if (!rs.next()) throw new UserNotFoundException();

            String fullname = rs.getString("fullname");

            u = new User(username, fullname);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new ConnectionFailureException();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return u;
    }

    /**
     * Loads usernames and full names of friends.
     * 
     * @param user The user whose friends to get
     */
    public List<User> getFriends(User user) {
        ResultSet rs = null;
        List<User> friends = new ArrayList<>();

        String queryString = "CALL get_friends(?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
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
            throw new ConnectionFailureException();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return friends;
    }

    /**
     * Load the friends of a friend.
     * If a friend is a mutual friend, load as {@link CommonFriend}.
     * 
     * @param user The user whose friends to get
     */
    public List<User> getFriendsOfFriendWithCommon(User user, User friend) {
        ResultSet rs = null;
        List<User> friends = new ArrayList<>();

        String queryString = "CALL get_friends_of_friend_with_common(?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, friend.getUsername());

            rs = stmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("friend_name");
                String fullname = rs.getString("fullname");
                boolean isMutual = rs.getBoolean("mutual");

                if (isMutual) {
                    CommonFriend f = new CommonFriend(username, fullname);
                    friends.add(f);
                } else {
                    User u = new User(username, fullname);
                    friends.add(u);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new ConnectionFailureException();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return friends;
    }

    public void unfriend(User user, String toUnfriend) {
        String queryString = "CALL unfriend(?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, toUnfriend);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new ConnectionFailureException();
        }
    }

    public List<String> getRequestUsernames(User user) {
        ResultSet rs = null;
        List<String> requestUsernames = new ArrayList<>();

        String queryString = "CALL get_requests(?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, user.getUsername());

            rs = stmt.executeQuery();
            while (rs.next()) {
                requestUsernames.add(rs.getString("sender"));
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new ConnectionFailureException();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return requestUsernames;
    }

    public void makeRequest(User sender, String recipient) {
        String queryString = "CALL make_request(?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, sender.getUsername());
            stmt.setString(2, recipient);

            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("Cannot request existing friend")) {
                throw new RequestExistingFriendException();
            } else if (e.getErrorCode() == 1452) {
                throw new UserNotFoundException();
            }
            System.err.println("SQLException: " + e.getMessage());
            throw new ConnectionFailureException();
        }
    }

    public void acceptRequest(String sender, User recipient) {
        String queryString = "CALL accept_request(?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, sender);
            stmt.setString(2, recipient.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new ConnectionFailureException();
        }
    }

    public void rejectRequest(String sender, User recipient) {
        String queryString = "CALL reject_request(?, ?)";

        try ( PreparedStatement stmt = connection().prepareStatement(queryString); ) {
            stmt.setString(1, sender);
            stmt.setString(2, recipient.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new ConnectionFailureException();
        }
    }
}
