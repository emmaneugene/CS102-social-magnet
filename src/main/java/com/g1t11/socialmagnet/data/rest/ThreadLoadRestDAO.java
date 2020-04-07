package com.g1t11.socialmagnet.data.rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.g1t11.socialmagnet.data.ServerException;
import com.g1t11.socialmagnet.data.ServerException.ErrorCode;
import com.g1t11.socialmagnet.model.social.Thread;

public class ThreadLoadRestDAO extends RestDAO {
    /**
     * Gets a thread from the perspective of user. This is needed to determine
     * whether the thread should be marked as a tagged thread or a thread viewed
     * under other conditions.
     * <p>
     * The distinction is necessary to determine if the prompt should include an
     * option to kill the thread. Threads posted on other friends walls are
     * included in one's news feed, but the user should not be able to kill
     * (delete or untag) the thread in this situation.
     * @param id The thread id.
     * @param username The username of the user retrieving the thread.
     */
    public Thread getThread(int threadId, String username) {
        Response response = getJSONInvocationOfTarget(
                "thread", username, String.valueOf(threadId)).get();

        if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
            throw new ServerException(ErrorCode.THREAD_NOT_FOUND);
        }

        Thread thread = null;
        try {
            String threadJson = response.readEntity(String.class);
            thread = mapper.readValue(threadJson, Thread.class);
        } catch (IOException e) {
            throw new ServerException("JSON parsing failure.");
        }

        return thread;
    }

    /**
     * Retrieves a list of latest threads on a user's news feed.
     * <p>
     * News feed threads include the union of all latest threads on the current
     * user's wall and threads posted to the current user's friends, not
     * including any gift threads.
     * @param username The user whose news feed to load.
     * @param limit The number of latest posts to retrieve.
     * @return A list of threads to be displayed on the news feed.
     * @see #getWallThreads(String, int)
     */
    public List<Thread> getNewsFeedThreads(String username, int limit) {
        Response response = getJSONInvocationOfTarget(
            "thread", username, "newsfeed", String.valueOf(limit)).get();

        if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }
        List<Thread> threads;
        try {
            String newsFeedJson = response.readEntity(String.class);
            threads = List.of(mapper.readValue(newsFeedJson, Thread[].class));
        } catch (IOException e) {
            throw new ServerException("JSON parsing failure.");
        }

        return threads;
    }

    /**
     * Retrieves a list of latest threads on a user's wall.
     * <p>
     * Wall threads include:
     * <ul>
     * <li> Non-gift threads by the current user.
     * <li> Threads to the current user's wall, including City Farmer gifts.
     * <li> Threads with the current user tagged.
     * </ul><p>
     * @param username The user whose wall to load.
     * @param limit The number of latest posts to retrieve.
     * @return Posts to be displayed on the wall.
     */
    public List<Thread> getWallThreads(String username, int limit) {
        Response response = getJSONInvocationOfTarget(
            "thread", username, "wall", String.valueOf(limit)).get();

        if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }
        List<Thread> threads;
        try {
            String newsFeedJson = response.readEntity(String.class);
            threads = List.of(mapper.readValue(newsFeedJson, Thread[].class));
        } catch (IOException e) {
            throw new ServerException("JSON parsing failure.");
        }

        return threads;
    }
}