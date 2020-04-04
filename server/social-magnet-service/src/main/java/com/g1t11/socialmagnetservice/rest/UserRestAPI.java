package com.g1t11.socialmagnetservice.rest;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.g1t11.socialmagnetservice.model.social.Friend;
import com.g1t11.socialmagnetservice.model.social.User;
import com.g1t11.socialmagnetservice.data.DatabaseException;
import com.g1t11.socialmagnetservice.data.UserDAO;
import com.g1t11.socialmagnetservice.data.DatabaseException.SQLErrorCode;

@Path("/user")
public class UserRestAPI {
    @Context
    ServletContext servContext;
    @Context
    HttpServletRequest servRequest;

    @PermitAll
    @GET
    @Path("{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUser(@PathParam("username") String username) {
        try {
            User user = UserDAO.getUser(username);

            return Response.status(Response.Status.OK).entity(user)
                    .type("application/json").build();
        } catch (DatabaseException e) {
            if (e.getCode().equals(SQLErrorCode.USER_NOT_FOUND)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(e.getMessage()).build();
            }
        }
    }

    @GET
    @Path("{username}/friends")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getFriends(@PathParam("username") String username) {
        try {
            List<User> friends = UserDAO.getFriends(username);

            return Response.status(Response.Status.OK).entity(friends)
                    .type("application/json").build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{username}/mutual/{friend}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getMutualFriends(@PathParam("username") String username,
            @PathParam("friend") String friendName) {
        try {
            List<Friend> friends = UserDAO.getFriendsOfFriendWithCommon(
                    username, friendName);

            return Response.status(Response.Status.OK).entity(friends)
                    .type("application/json").build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{username}/unfriend")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response unfriend(@PathParam("username") String username, String body) {
        try {
            String friendName = body;
            UserDAO.unfriend(username, friendName);

            return Response.status(Response.Status.OK)
                    .entity(String.format("Unfriended %s!", friendName)).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{username}/requests")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRequests(@PathParam("username") String username) {
        try {
            List<String> requests = UserDAO.getRequestUsernames(username);

            return Response.status(Response.Status.OK).entity(requests)
                    .type("application/json").build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("{username}/request")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response makeRequest(@PathParam("username") String username,
            String body) {
        try {
            String friendName = body;
            UserDAO.makeRequest(username, friendName);

            return Response.status(Response.Status.OK)
                    .entity(String.format(
                            "Sent %s a friend request!", friendName))
                    .build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getCode().value).build();
        }
    }

    @PUT
    @Path("{username}/accept")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response acceptRequest(@PathParam("username") String username,
            String body) {
        try {
            String friendName = body;
            UserDAO.acceptRequest(friendName, username);

            return Response.status(Response.Status.OK)
                    .entity(String.format(
                            "Accepted %s as a friend!", friendName))
                    .build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{username}/reject")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response rejectRequest(@PathParam("username") String username,
            String body) {
        try {
            String friendName = body;
            UserDAO.rejectRequest(friendName, username);

            return Response.status(Response.Status.OK)
                    .entity(String.format(
                            "Rejected %s as a friend!", friendName))
                    .build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }
}