package com.g1t11.socialmagnet.data.rest;

import java.io.IOException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.g1t11.socialmagnet.data.ServerException;
import com.g1t11.socialmagnet.data.ServerException.ErrorCode;
import com.g1t11.socialmagnet.model.social.User;

import org.json.JSONObject;

public class CredentialsRestDAO extends RestDAO {
    /**
     * Get the logged-in user if login is successful. Otherwise, return null.
     * @param username The inputted username.
     * @param password The inputted password.
     * @return A user model if the login is successful, else null.
     */
    public User login(String username, String password) {
        JSONObject obj = new JSONObject();
        obj.put("username", username);
        obj.put("password", password);
        Entity<String> reqBody
                = Entity.entity(obj.toString(), MediaType.APPLICATION_JSON);
        Response response = getJSONInvocationOfTarget("login").post(reqBody);

        if (response.getStatus() == Status.BAD_REQUEST.getStatusCode()) {
            return null;
        } else if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
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
     * Register a new user on the database.
     * @param username The new username.
     * @param fullname The new fullname.
     * @param password The new password.
     * @throws ServerException With REGISTER_EXISTING_USER code if the username
     * is already taken.
     */
    public void register(String username, String fullname, String password) {
        JSONObject obj = new JSONObject();
        obj.put("username", username);
        obj.put("fullname", fullname);
        obj.put("password", password);
        Response response = getJSONInvocationOfTarget("register").post(
            Entity.entity(obj.toString(), MediaType.APPLICATION_JSON));

        if (response.getStatus() == Status.CONFLICT.getStatusCode()) {
            throw new ServerException(ErrorCode.REGISTER_EXISTING_USER);
        } else if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }
    }

    /**
     * Check if the user account with the name username exists.
     * @param username The user to check.
     * @return The user exists in the database.
     */
    public boolean userExists(String username) {
        Response response = getTextInvocationOfTarget("exists", username).get();

        if (response.getStatus() == Status.OK.getStatusCode()) {
            return true;
        } else {
            return false;
        }
    }
}