package com.g1t11.socialmagnet.data.rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.g1t11.socialmagnet.data.ServerException;
import com.g1t11.socialmagnet.model.farm.Crop;

import org.json.JSONObject;

public class StoreRestDAO extends RestDAO {
    /**
     * Get all currently available crops on the database.
     * @return A list of all available crops in ascending order.
     */
    public List<Crop> getStoreItems() {
        Response response = getJSONInvocationOfTarget("store").get();
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }

        List<Crop> store = null;
        try {
            String storeJson = response.readEntity(String.class);
            store = List.of(mapper.readValue(storeJson, Crop[].class));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new ServerException("JSON parsing failure.");
        }
        return store;
    }

    /**
     * Purchase an amount of crops from the store and add them to the user's
     * inventory, and update the user's wealth.
     * @param buyerName The user who is buying crops.
     * @param cropName The crop to purchase.
     * @param amount The number of bags of seeds to purchase.
     * @return True if the purchase was successful, or false if it was not.
     */
    public boolean purchaseCrop(String buyerName, String cropName, int amount) {
        JSONObject obj = new JSONObject();
        obj.put("buyerName", buyerName);
        obj.put("cropName", cropName);
        obj.put("amount", amount);
        Entity<String> reqBody = Entity.json(obj.toString());
        Response response = getTextInvocationOfTarget("store", "purchase")
                .post(reqBody);
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }
        String successStr = response.readEntity(String.class);
        return Boolean.parseBoolean(successStr);
    }
}