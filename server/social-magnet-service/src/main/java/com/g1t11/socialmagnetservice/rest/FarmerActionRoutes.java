package com.g1t11.socialmagnetservice.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.g1t11.socialmagnetservice.data.DatabaseException;
import com.g1t11.socialmagnetservice.data.FarmerActionDAO;
import com.g1t11.socialmagnetservice.model.farm.StealingRecord;

import org.json.JSONObject;

@Path("/farm")
public class FarmerActionRoutes {
    @POST
    @Path("{username}/plant")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response plantCrop(
            @PathParam("username") String username, String body) {
        JSONObject obj = new JSONObject(body);
        int plotNumber = obj.getInt("plotNumber");
        String cropName = obj.getString("cropName");
        try {
            FarmerActionDAO.plantCrop(username, plotNumber, cropName);

            return Response.status(Response.Status.OK).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("{username}/clear")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response clearPlot(
            @PathParam("username") String username, String body) {
        JSONObject obj = new JSONObject(body);
        int plotNumber = obj.getInt("plotNumber");
        try {
            FarmerActionDAO.clearPlot(username, plotNumber);

            return Response.status(Response.Status.OK).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("{username}/harvest")
    public Response harvest(@PathParam("username") String username) {
        try {
            FarmerActionDAO.harvest(username);

            return Response.status(Response.Status.OK).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("{username}/steal/{victim}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response steal(
            @PathParam("username") String username,
            @PathParam("victim") String victimName) {
        try {
            List<StealingRecord> stolenCrops =
                FarmerActionDAO.steal(username, victimName);

            return Response.status(Response.Status.OK).entity(stolenCrops)
                    .type(MediaType.APPLICATION_JSON).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("{username}/send")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response sendGifts(
            @PathParam("username") String username, String body) {
        JSONObject obj = new JSONObject(body);
        String[] recipients = obj.getJSONArray("recipients").toList().stream()
                .map(x -> (String) x)
                .toArray(size -> new String[size]);
        String cropName = obj.getString("cropName");
        try {
            FarmerActionDAO.sendGifts(username, recipients, cropName);

            return Response.status(Response.Status.OK).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("{username}/accept_gifts")
    public Response acceptGifts(@PathParam("username") String username) {
        try {
            FarmerActionDAO.acceptGifts(username);

            return Response.status(Response.Status.OK).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }
}