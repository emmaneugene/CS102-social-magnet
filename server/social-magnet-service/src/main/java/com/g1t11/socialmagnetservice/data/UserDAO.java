package com.g1t11.socialmagnetservice.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.g1t11.socialmagnetservice.data.DatabaseException.SQLErrorCode;
import com.g1t11.socialmagnetservice.model.social.Friend;
import com.g1t11.socialmagnetservice.model.social.User;

public class UserDAO extends DAO {
    /**
     * Get user information from the database.
     * <p>
     * This method should not be used to verify the existence of a user, but
     * to simply load user information.
     * @param username The unique username of a user.
     * @return The user with the specified username.
     * @see CredentialsDAO#userExists(String)
     */
    public static User getUser(String username) {
        ResultSet rs = null;
        User u = null;

        String queryString = "CALL get_user(?)";

        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new DatabaseException(SQLErrorCode.USER_NOT_FOUND);
            }

            String fullname = rs.getString("fullname");

            u = new User(username, fullname);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return u;
    }

    /**
     * Loads usernames and full names of friends.
     * @param username The username of the user whose friends to get.
     * @return A list of users who are friends.
     */
    public static List<User> getFriends(String username) {
        ResultSet rs = null;
        List<User> friends = new ArrayList<>();

        String queryString = "CALL get_friends(?)";

        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            while (rs.next()) {
                User u = new User(
                    rs.getString("username"),
                    rs.getString("fullname")
                );
                friends.add(u);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return friends;
    }

    /**
     * Load the friends of a friend. If a friend is a mutual friend, load as
     * {@link CommonFriend}.
     * @param username The username of the current user.
     * @param friendName The username of the friend whose friends to get.
     * @return A list of usernames of friends with common friends.
     */
    public static List<Friend> getFriendsOfFriendWithCommon(
                String username, String friendName) {
        ResultSet rs = null;
        List<Friend> friends = new ArrayList<>();

        String queryString = "CALL get_friends_of_friend_with_common(?, ?)";

        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setString(1, username);
            stmt.setString(2, friendName);

            rs = stmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("friend_name");
                String fullname = rs.getString("fullname");
                boolean isMutual = rs.getBoolean("mutual");

                if (isMutual) {
                    Friend f = new Friend(name, fullname, true);
                    friends.add(f);
                } else {
                    Friend f = new Friend(name, fullname, false);
                    friends.add(f);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return friends;
    }

    /**
     * Removes a friend relation between two users on the database.
     * @param username The username of the current user.
     * @param toUnfriend The username of the user to unfriend.
     */
    public static void unfriend(String username, String toUnfriend) {
        String queryString = "CALL unfriend(?, ?)";

        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setString(1, username);
            stmt.setString(2, toUnfriend);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }

    /**
     * Get all pending friend requests.
     * @param username The username of the user to get friend requests for.
     * @return A list usernames of users sending friend requests.
     */
    public static List<String> getRequestUsernames(String username) {
        ResultSet rs = null;
        List<String> requestUsernames = new ArrayList<>();

        String queryString = "CALL get_requests(?)";

        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            while (rs.next()) {
                requestUsernames.add(rs.getString("sender"));
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return requestUsernames;
    }

    /**
     * Add a friend request to the database.
     * @param sender The username of the user making a friend request.
     * @param recipient The username of the user to receive the friend request.
     */
    public static void makeRequest(String sender, String recipient) {
        String queryString = "CALL make_request(?, ?)";

        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setString(1, sender);
            stmt.setString(2, recipient);

            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("Cannot request existing friend")) {
                throw new DatabaseException(
                        SQLErrorCode.REQUEST_EXISTING_FRIEND);
            } else if (e.getMessage().contains("Cannot request self")) {
                throw new DatabaseException(
                        SQLErrorCode.REQUEST_SELF);
            } else if (e.getErrorCode() == 1452) {
                throw new DatabaseException(SQLErrorCode.USER_NOT_FOUND);
            }
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }

    /**
     * Accept a friend request on the database.
     * @param sender The username of the user who made the friend request.
     * @param recipient The username of the user who received the friend
     * request.
     */
    public static void acceptRequest(String sender, String recipient) {
        String queryString = "CALL accept_request(?, ?)";

        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setString(1, sender);
            stmt.setString(2, recipient);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }

    /**
     * Reject a friend request on the database.
     * @param sender The username of the user who made the friend request.
     * @param recipient The username of the user who received the friend
     * request.
     */
    public static void rejectRequest(String sender, String recipient) {
        String queryString = "CALL reject_request(?, ?)";

        try ( PreparedStatement stmt = conn().prepareStatement(queryString); ) {
            stmt.setString(1, sender);
            stmt.setString(2, recipient);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw new DatabaseException(e);
        }
    }
}
