package com.g1t11.socialmagnetservice.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.g1t11.socialmagnetservice.data.CredentialsDAO;
import com.g1t11.socialmagnetservice.data.DatabaseException;
import com.g1t11.socialmagnetservice.data.DatabaseException.SQLErrorCode;
import com.g1t11.socialmagnetservice.model.social.User;

import org.json.JSONObject;

@Path("/")
public class CredentialsRoutes {
    @GET
    @Path("login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(String bodyJson) {
        JSONObject obj = new JSONObject(bodyJson);
        String username = obj.getString("username");
        String password = obj.getString("password");
        try {
            User user = CredentialsDAO.login(username, password);
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                return Response.status(Response.Status.OK).entity(user)
                        .type(MediaType.APPLICATION_JSON).build();
            }
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_GATEWAY)
                    .entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("register")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response register(String bodyJson) {
        JSONObject obj = new JSONObject(bodyJson);
        String username = obj.getString("username");
        String fullname = obj.getString("fullname");
        String password = obj.getString("password");
        try {
            CredentialsDAO.register(username, fullname, password);

            return Response.status(Response.Status.OK).build();
        } catch (DatabaseException e) {
            if (e.getCode().equals(SQLErrorCode.REGISTER_EXISTING_USER)) {
                return Response.status(Response.Status.CONFLICT).build();
            } else {
                return Response.status(Response.Status.BAD_GATEWAY)
                        .entity(e.getMessage()).build();
            }
        }
    }

    @GET
    @Path("exists/{username}")
    public Response userExists(@PathParam("username") String username) {
        try {
            if (CredentialsDAO.userExists(username)) {
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_GATEWAY)
                    .entity(e.getMessage()).build();
        }
    }
}