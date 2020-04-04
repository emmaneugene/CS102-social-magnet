package com.g1t11.socialmagnet.data.rest;

import java.io.IOException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.g1t11.socialmagnet.data.DatabaseException;
import com.g1t11.socialmagnet.data.DatabaseException.SQLErrorCode;
import com.g1t11.socialmagnet.model.social.User;

import org.json.JSONObject;

public class CredentialsRestDAO extends RestDAO {
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
            throw new DatabaseException(response.readEntity(String.class));
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

    public void register(String username, String fullname, String password) {
        JSONObject obj = new JSONObject();
        obj.put("username", username);
        obj.put("fullname", fullname);
        obj.put("password", password);
        Response response = getJSONInvocationOfTarget("register").post(
            Entity.entity(obj.toString(), MediaType.APPLICATION_JSON));

        if (response.getStatus() == Status.CONFLICT.getStatusCode()) {
            throw new DatabaseException(SQLErrorCode.REGISTER_EXISTING_USER);
        } else if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }
    }

    public boolean userExists(String username) {
        Response response = getInvocationOfTarget("exists", username).get();

        if (response.getStatus() == Status.OK.getStatusCode()) {
            return true;
        } else {
            return false;
        }
    }
}