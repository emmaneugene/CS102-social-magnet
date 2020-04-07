package com.g1t11.socialmagnetservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class HomeRoute {
    @Path("")
    @GET
    public Response getHome() {
        String message = "Social Magnet Service running!";
        return Response.status(Response.Status.OK)
                .entity(message).type(MediaType.TEXT_PLAIN).build();
    }
}