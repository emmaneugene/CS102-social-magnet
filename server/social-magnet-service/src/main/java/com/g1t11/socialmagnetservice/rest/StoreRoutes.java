package com.g1t11.socialmagnetservice.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.g1t11.socialmagnetservice.data.DatabaseException;
import com.g1t11.socialmagnetservice.data.StoreDAO;
import com.g1t11.socialmagnetservice.model.farm.Crop;

import org.json.JSONObject;

@Path("/store")
public class StoreRoutes {
    @Path("")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getStoreItems() {
        try {
            List<Crop> crops = StoreDAO.getStoreItems();

            return Response.status(Response.Status.OK).entity(crops)
                    .type(MediaType.APPLICATION_JSON).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }

    @Path("purchase")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public Response purchaseCrop(String body) {
        JSONObject obj = new JSONObject(body);
        String buyerName = obj.getString("buyerName");
        String cropName = obj.getString("cropName");
        int amount = obj.getInt("amount");

        boolean isSuccessful = false;
        try {
            isSuccessful = StoreDAO.purchaseCrop(buyerName, cropName, amount);

            return Response.status(Response.Status.OK)
                    .entity(isSuccessful).build();
        } catch (DatabaseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }
}