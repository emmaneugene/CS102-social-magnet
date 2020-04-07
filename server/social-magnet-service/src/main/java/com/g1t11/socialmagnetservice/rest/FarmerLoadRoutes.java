package com.g1t11.socialmagnetservice.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.g1t11.socialmagnetservice.data.DatabaseException;
import com.g1t11.socialmagnetservice.data.FarmerLoadDAO;
import com.g1t11.socialmagnetservice.model.farm.Farmer;
import com.g1t11.socialmagnetservice.model.farm.Plot;

@Path("/farm")
public class FarmerLoadRoutes {
    @GET
    @Path("{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getFarmer(@PathParam("username") String username) {
        try {
            Farmer farmer = FarmerLoadDAO.getFarmer(username);

            return Response.status(Response.Status.OK).entity(farmer)
                    .type(MediaType.APPLICATION_JSON).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{username}/inventory")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getInventoryCrops(@PathParam("username") String username) {
        try {
            Map<String, Integer> inventory
                    = FarmerLoadDAO.getInventoryCrops(username);

            return Response.status(Response.Status.OK).entity(inventory)
                    .type(MediaType.APPLICATION_JSON).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{username}/plots")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPlots(@PathParam("username") String username,
            @QueryParam("count") int maxPlotCount) {
        try {
            List<Plot> plots = FarmerLoadDAO.getPlots(username, maxPlotCount);
            System.out.println(plots.toString());

            return Response.status(Response.Status.OK).entity(plots)
                    .type(MediaType.APPLICATION_JSON).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{username}/gift_count_today")
    @Produces({MediaType.TEXT_PLAIN})
    public Response getGiftCountToday(@PathParam("username") String username) {
        try {
            int count = FarmerLoadDAO.getGiftCountToday(username);

            return Response.status(Response.Status.OK).entity(count)
                    .type(MediaType.TEXT_PLAIN).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{username}/sent_gift_to_users_today")
    @Produces({MediaType.APPLICATION_JSON})
    public Response sentGiftToUsersToday(
            @PathParam("username") String username,
            @QueryParam("recipients") List<String> recipients) {
        try {
            Map<String, Boolean> sentMap = FarmerLoadDAO.sentGiftToUsersToday(
                    username, recipients.toArray(new String[0]));

            return Response.status(Response.Status.OK).entity(sentMap)
                    .type(MediaType.APPLICATION_JSON).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }
}