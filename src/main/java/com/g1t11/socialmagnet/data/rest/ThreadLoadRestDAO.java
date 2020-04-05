package com.g1t11.socialmagnet.data.rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.g1t11.socialmagnet.data.DatabaseException;
import com.g1t11.socialmagnet.data.DatabaseException.SQLErrorCode;
import com.g1t11.socialmagnet.model.social.Thread;

public class ThreadLoadRestDAO extends RestDAO {
    public Thread getThread(int threadId, String username) {
        Response response = getJSONInvocationOfTarget(
                "thread", username, String.valueOf(threadId)).get();

        if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
            throw new DatabaseException(SQLErrorCode.THREAD_NOT_FOUND);
        }

        Thread thread = null;
        try {
            String threadJson = response.readEntity(String.class);
            thread = mapper.readValue(threadJson, Thread.class);
        } catch (IOException e) {
            throw new DatabaseException("JSON parsing failure.");
        }

        return thread;
    }

    public List<Thread> getNewsFeedThreads(String username, int limit) {
        Response response = getJSONInvocationOfTarget(
            "thread", username, "newsfeed", String.valueOf(limit)).get();

        if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }
        List<Thread> threads;
        try {
            String newsFeedJson = response.readEntity(String.class);
            threads = List.of(mapper.readValue(newsFeedJson, Thread[].class));
        } catch (IOException e) {
            throw new DatabaseException("JSON parsing failure.");
        }

        return threads;
    }

    public List<Thread> getWallThreads(String username, int limit) {
        Response response = getJSONInvocationOfTarget(
            "thread", username, "wall", String.valueOf(limit)).get();

        if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }
        List<Thread> threads;
        try {
            String newsFeedJson = response.readEntity(String.class);
            threads = List.of(mapper.readValue(newsFeedJson, Thread[].class));
        } catch (IOException e) {
            throw new DatabaseException("JSON parsing failure.");
        }

        return threads;
    }
}