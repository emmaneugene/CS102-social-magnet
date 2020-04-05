package com.g1t11.socialmagnet.data.rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.g1t11.socialmagnet.data.DatabaseException;
import com.g1t11.socialmagnet.data.DatabaseException.SQLErrorCode;
import com.g1t11.socialmagnet.model.social.Thread;

import org.json.JSONArray;
import org.json.JSONObject;

public class ThreadActionRestDAO extends RestDAO {
    public int addThread(String fromUser, String toUser,
            String content, List<String> usernameTags) {
        JSONObject obj = new JSONObject();
        obj.put("toUser", toUser);
        obj.put("content", content);
        JSONArray tagArr = new JSONArray(usernameTags);
        obj.put("tags", tagArr);
        Entity<String> reqBody = Entity.json(obj.toString());
        Response response = getTextInvocationOfTarget(
                "thread", fromUser, "new").post(reqBody);

        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }

        int id = response.readEntity(Integer.class);
        return id;
    }

    public void removeTag(int threadId, String username) {
        Response response = getTextInvocationOfTarget(
                "thread", username,
                String.valueOf(threadId), "untag").delete();
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }
    }

    public void deleteThread(int threadId, String username) {
        Response response = getTextInvocationOfTarget(
                "thread", username,
                String.valueOf(threadId), "delete").delete();
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }
    }

    public void replyToThread(int threadId, String username, String content) {
        Response response = getTextInvocationOfTarget(
                "thread", username, String.valueOf(threadId), "reply").post(
                        Entity.text(content));
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }
    }

    public void toggleLikeThread(int threadId, String username) {
        Response response = getTextInvocationOfTarget(
                "thread", username,
                String.valueOf(threadId), "like").put(Entity.text(""));
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }
    }

    public void toggleDislikeThread(int threadId, String username) {
        Response response = getTextInvocationOfTarget(
                "thread", username,
                String.valueOf(threadId), "dislike").put(Entity.text(""));
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }
    }
}