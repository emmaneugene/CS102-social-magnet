package com.g1t11.socialmagnet.data;

import java.io.IOException;
import java.util.List;

import com.g1t11.socialmagnet.data.DatabaseException.SQLErrorCode;
import com.g1t11.socialmagnet.model.social.User;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UserRestDAO extends RestDAO {
    public User getUser(String username) {
        WebTarget target = getTarget("user", username);
        Invocation.Builder invocationBuilder =
            target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        if (response.getStatus() != 200) {
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
        WebTarget target = getTarget("user", username, "friends");
        Invocation.Builder invocationBuilder =
            target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        if (response.getStatus() != 200) {
            throw new DatabaseException(SQLErrorCode.USER_NOT_FOUND);
        }

        List<User> friends = null;
        try {
            String friendsJson = response.readEntity(String.class);
            System.out.println(friendsJson);

            friends = List.of(mapper.readValue(friendsJson, User[].class));
        } catch (IOException e) {
            throw new DatabaseException("JSON parsing failure.");
        }

        return friends;
    }
}