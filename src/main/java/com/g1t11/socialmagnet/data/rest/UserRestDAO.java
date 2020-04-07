package com.g1t11.socialmagnet.data.rest;

import java.io.IOException;
import java.util.List;

import com.g1t11.socialmagnet.data.ServerException;
import com.g1t11.socialmagnet.data.ServerException.ErrorCode;
import com.g1t11.socialmagnet.model.social.Friend;
import com.g1t11.socialmagnet.model.social.User;
import javax.ws.rs.core.Response.Status;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UserRestDAO extends RestDAO {
    /**
     * Get user information from the database.
     * <p>
     * This method should not be used to verify the existence of a user, but
     * to simply load user information.
     * @param username The unique username of a user.
     * @return The user with the specified username.
     * @see CredentialsRestDAO#userExists(String)
     */
    public User getUser(String username) {
        Response response = getJSONInvocationOfTarget("user", username).get();

        if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
            throw new ServerException(ErrorCode.USER_NOT_FOUND);
        }

        User user = null;
        try {
            String userJson = response.readEntity(String.class);
            user = mapper.readValue(userJson, User.class);
        } catch (IOException e) {
            throw new ServerException("JSON parsing failure.");
        }

        return user;
    }

    /**
     * Loads usernames and full names of friends.
     * @param username The username of the user whose friends to get.
     * @return A list of users who are friends.
     */
    public List<User> getFriends(String username) {
        Response response = getJSONInvocationOfTarget(
                "user", username, "friends").get();

        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }

        List<User> friends = null;
        try {
            String friendsJson = response.readEntity(String.class);
            friends = List.of(mapper.readValue(friendsJson, User[].class));
        } catch (IOException e) {
            throw new ServerException("JSON parsing failure.");
        }

        return friends;
    }

    /**
     * Load the friends of a friend.
     * @param username The username of the current user.
     * @param friendName The username of the friend whose friends to get.
     * @return A list of friends, with the {@link Friend#isMutual()} attribute
     * set to true for mutual friends.
     */
    public List<Friend> getFriendsOfFriendWithCommon(String username,
            String friendName) {
        Response response = getJSONInvocationOfTarget(
                "user", username, "mutual", friendName).get();
        if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }

        List<Friend> friends = null;
        try {
            String friendsJson = response.readEntity(String.class);
            friends = List.of(mapper.readValue(friendsJson, Friend[].class));
        } catch (IOException e) {
            throw new ServerException("JSON parsing failure.");
        }

        return friends;
    }

    /**
     * Removes a friend relation between two users on the database.
     * @param username The username of the current user.
     * @param toUnfriend The username of the user to unfriend.
     */
    public void unfriend(String username, String toUnfriend) {
        Response response = getTextInvocationOfTarget(
                "user", username, "unfriend")
                .put(Entity.entity(toUnfriend, MediaType.TEXT_PLAIN));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }
    }

    /**
     * Get all pending friend requests.
     * @param username The username of the user to get friend requests for.
     * @return A list usernames of users who sent friend requests.
     */
    public List<String> getRequestUsernames(String username) {
        Response response = getJSONInvocationOfTarget(
                "user", username, "requests").get();
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException("Could not get requests.");
        }

        List<String> requestNames = null;
        try {
            String requestsJson = response.readEntity(String.class);
            requestNames = List.of(
                    mapper.readValue(requestsJson, String[].class));
        } catch (IOException e) {
            throw new ServerException("JSON parsing failure.");
        }

        return requestNames;
    }

    /**
     * Add a friend request to the database.
     * @param sender The username of the user making a friend request.
     * @param recipient The username of the user to receive the friend request.
     */
    public void makeRequest(String sender, String recipient) {
        Response response = getTextInvocationOfTarget(
                "user", sender, "request")
                .post(Entity.entity(recipient, MediaType.TEXT_PLAIN));
        if (response.getStatus() == Status.BAD_REQUEST.getStatusCode()) {
            Integer code = response.readEntity(Integer.class);
            throw new ServerException(code);
        }
    }

    /**
     * Accept a friend request on the database.
     * @param sender The username of the user who made the friend request.
     * @param recipient The username of the user who received the friend
     * request.
     */
    public void acceptRequest(String sender, String recipient) {
        Response response = getJSONInvocationOfTarget(
                "user", recipient, "accept")
                .put(Entity.entity(sender, MediaType.TEXT_PLAIN));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException("Could not accept request.");
        }
    }

    /**
     * Reject a friend request on the database.
     * @param sender The username of the user who made the friend request.
     * @param recipient The username of the user who received the friend
     * request.
     */
    public void rejectRequest(String sender, String recipient) {
        Response response = getJSONInvocationOfTarget(
                "user", recipient, "reject")
                .put(Entity.entity(sender, MediaType.TEXT_PLAIN));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException("Could not reject request.");
        }
    }
}