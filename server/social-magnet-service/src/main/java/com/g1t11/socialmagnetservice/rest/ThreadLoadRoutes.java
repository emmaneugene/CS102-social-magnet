package com.g1t11.socialmagnetservice.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.g1t11.socialmagnetservice.data.DatabaseException;
import com.g1t11.socialmagnetservice.data.ThreadLoadDAO;
import com.g1t11.socialmagnetservice.model.social.Thread;

@Path("/thread")
public class ThreadLoadRoutes {
    @GET
    @Path("{username}/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getThread(@PathParam("id") int threadId,
            @PathParam("username") String username) {
        try {
            Thread thread = ThreadLoadDAO.getThread(threadId, username);

            return Response.status(Response.Status.OK).entity(thread)
                    .type(MediaType.APPLICATION_JSON).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{username}/newsfeed/{limit}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getNewsFeedThreads(
            @PathParam("username") String username,
            @PathParam("limit") int limit) {
        try {
            List<Thread> newsFeedThreads
                    = ThreadLoadDAO.getNewsFeedThreads(username, limit);

            return Response.status(Response.Status.OK).entity(newsFeedThreads)
                    .type(MediaType.APPLICATION_JSON).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{username}/wall/{limit}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getWallThreads(
            @PathParam("username") String username,
            @PathParam("limit") int limit) {
        try {
            List<Thread> newsFeedThreads
                    = ThreadLoadDAO.getWallThreads(username, limit);

            return Response.status(Response.Status.OK).entity(newsFeedThreads)
                    .type(MediaType.APPLICATION_JSON).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }
}