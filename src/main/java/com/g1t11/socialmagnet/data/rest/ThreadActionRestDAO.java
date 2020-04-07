package com.g1t11.socialmagnet.data.rest;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.g1t11.socialmagnet.data.ServerException;

import org.json.JSONArray;
import org.json.JSONObject;

public class ThreadActionRestDAO extends RestDAO {
    /**
     * Post a new thread from a user to another user's wall on the database,
     * and add the tags associated to the database.
     * @param fromUser The user posting the thread.
     * @param toUser The user receiving the thread.
     * @param content The content of the thread.
     * @param usernameTags A list of usernames to tag the thread with.
     * @return The new id of the thread.
     */
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
            throw new ServerException(response.readEntity(String.class));
        }

        int id = response.readEntity(Integer.class);
        return id;
    }

    /**
     * Remove a tag from a thread on the database.
     * @param threadId The ID of the thread to untag.
     * @param username The username tag to remove.
     */
    public void removeTag(int threadId, String username) {
        Response response = getTextInvocationOfTarget(
                "thread", username,
                String.valueOf(threadId), "untag").delete();
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }
    }

    /**
     * Deletes a thread from the database, given the deleting user is
     * authorized to do so.
     * @param threadId The ID of the thread to delete.
     * @param username The user deleting the thread.
     */
    public void deleteThread(int threadId, String username) {
        Response response = getTextInvocationOfTarget(
                "thread", username,
                String.valueOf(threadId), "delete").delete();
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }
    }

    /**
     * Add a reply to a thread on the database.
     * @param threadId The ID of the thread to reply to.
     * @param username The username of the user replying to the thread.
     * @param content The content of the reply.
     */
    public void replyToThread(int threadId, String username, String content) {
        Response response = getTextInvocationOfTarget(
                "thread", username, String.valueOf(threadId), "reply").post(
                        Entity.text(content));
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }
    }

    /**
     * Toggle between adding or removing a user as a liker of a thread.
     * @param threadId The ID of the thread to like.
     * @param username The username of the user liking the thread.
     */
    public void toggleLikeThread(int threadId, String username) {
        Response response = getTextInvocationOfTarget(
                "thread", username,
                String.valueOf(threadId), "like").put(Entity.text(""));
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }
    }

    /**
     * Toggle between adding or removing a user as a disliker of a thread.
     * @param threadId The ID of the thread to like.
     * @param username The username of the user disliking the thread.
     */
    public void toggleDislikeThread(int threadId, String username) {
        Response response = getTextInvocationOfTarget(
                "thread", username,
                String.valueOf(threadId), "dislike").put(Entity.text(""));
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }
    }
}