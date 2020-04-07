package com.g1t11.socialmagnet.data.rest;

import java.io.IOException;
import java.util.List;

import com.g1t11.socialmagnet.data.DatabaseException;
import com.g1t11.socialmagnet.data.DatabaseException.SQLErrorCode;
import com.g1t11.socialmagnet.model.social.Friend;
import com.g1t11.socialmagnet.model.social.User;
import javax.ws.rs.core.Response.Status;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UserRestDAO extends RestDAO {
    public User getUser(String username) {
        Response response = getJSONInvocationOfTarget("user", username).get();

        if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
            throw new DatabaseException(SQLErrorCode.USER_NOT_FOUND);
        }

        User user = null;
        try {
            String userJson = response.readEntity(String.class);
            user = mapper.readValue(userJson, User.class);
        } catch (IOException e) {
            throw new DatabaseException("JSON parsing failure.");
        }

        return user;
    }

    public List<User> getFriends(String username) {
        Response response = getJSONInvocationOfTarget(
                "user", username, "friends").get();

        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }

        List<User> friends = null;
        try {
            String friendsJson = response.readEntity(String.class);
            friends = List.of(mapper.readValue(friendsJson, User[].class));
        } catch (IOException e) {
            throw new DatabaseException("JSON parsing failure.");
        }

        return friends;
    }

    public List<Friend> getFriendsOfFriendWithCommon(String username,
            String friendName) {
        Response response = getJSONInvocationOfTarget(
                "user", username, "mutual", friendName).get();
        if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }

        List<Friend> friends = null;
        try {
            String friendsJson = response.readEntity(String.class);
            friends = List.of(mapper.readValue(friendsJson, Friend[].class));
        } catch (IOException e) {
            throw new DatabaseException("JSON parsing failure.");
        }

        return friends;
    }

    public void unfriend(String username, String toUnfriend) {
        Response response = getTextInvocationOfTarget(
                "user", username, "unfriend")
                .put(Entity.entity(toUnfriend, MediaType.TEXT_PLAIN));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }
    }

    public List<String> getRequestUsernames(String username) {
        Response response = getJSONInvocationOfTarget(
                "user", username, "requests").get();
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new DatabaseException("Could not get requests.");
        }

        List<String> requestNames = null;
        try {
            String requestsJson = response.readEntity(String.class);
            requestNames = List.of(
                    mapper.readValue(requestsJson, String[].class));
        } catch (IOException e) {
            throw new DatabaseException("JSON parsing failure.");
        }

        return requestNames;
    }

    public void makeRequest(String sender, String recipient) {
        Response response = getTextInvocationOfTarget(
                "user", sender, "request")
                .post(Entity.entity(recipient, MediaType.TEXT_PLAIN));
        if (response.getStatus() == Status.BAD_REQUEST.getStatusCode()) {
            Integer code = response.readEntity(Integer.class);
            throw new DatabaseException(code);
        }
    }

    public void acceptRequest(String sender, String recipient) {
        Response response = getJSONInvocationOfTarget(
                "user", recipient, "accept")
                .put(Entity.entity(sender, MediaType.TEXT_PLAIN));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new DatabaseException("Could not accept request.");
        }
    }

    public void rejectRequest(String sender, String recipient) {
        Response response = getJSONInvocationOfTarget(
                "user", recipient, "reject")
                .put(Entity.entity(sender, MediaType.TEXT_PLAIN));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new DatabaseException("Could not reject request.");
        }
    }
}