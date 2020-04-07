package com.g1t11.socialmagnetservice.rest;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.g1t11.socialmagnetservice.data.DatabaseException;
import com.g1t11.socialmagnetservice.data.ThreadActionDAO;

import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

@Path("/thread")
public class ThreadActionRoutes {
    @POST
    @Path("{username}/new")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public Response addThread(@PathParam("username") String fromUser, String body) {
        JSONObject obj = new JSONObject(body);
        String toUser = obj.getString("toUser");
        String content = obj.getString("content");
        List<String> tags = obj.getJSONArray("tags").toList().stream()
                .map(x -> (String) x)
                .collect(Collectors.toList());
        try {
            int id = ThreadActionDAO.addThread(fromUser, toUser, content, tags);

            return Response.status(Response.Status.OK).entity(id).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{username}/{id}/untag")
    @Produces({MediaType.TEXT_PLAIN})
    public Response removeTag(@PathParam("username") String username,
            @PathParam("id") int threadId) {
        try {
            ThreadActionDAO.removeTag(threadId, username);

            return Response.status(Response.Status.OK).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{username}/{id}/delete")
    @Produces({MediaType.TEXT_PLAIN})
    public Response deleteThread(@PathParam("username") String username,
            @PathParam("id") int threadId) {
        try {
            ThreadActionDAO.deleteThread(threadId, username);

            return Response.status(Response.Status.OK).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("{username}/{id}/reply")
    @Consumes({MediaType.TEXT_PLAIN})
    @Produces({MediaType.TEXT_PLAIN})
    public Response replyToThread(@PathParam("username") String username,
            @PathParam("id") int threadId, String body) {
        try {
            ThreadActionDAO.replyToThread(threadId, username, body);

            return Response.status(Response.Status.OK).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{username}/{id}/like")
    @Produces({MediaType.TEXT_PLAIN})
    public Response toggleLikeThread(@PathParam("username") String username,
            @PathParam("id") int threadId) {
        try {
            ThreadActionDAO.toggleLikeThread(threadId, username);

            return Response.status(Response.Status.OK).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{username}/{id}/dislike")
    @Produces({MediaType.TEXT_PLAIN})
    public Response toggleDislikeThread(@PathParam("username") String username,
            @PathParam("id") int threadId) {
        try {
            ThreadActionDAO.toggleDislikeThread(threadId, username);

            return Response.status(Response.Status.OK).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }
}