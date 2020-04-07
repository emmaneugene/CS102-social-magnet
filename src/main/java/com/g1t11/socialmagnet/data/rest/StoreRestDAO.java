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
    public List<Crop> getStoreItems() {
        Response response = getJSONInvocationOfTarget("store").get();
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }

        List<Crop> store = null;
        try {
            String storeJson = response.readEntity(String.class);
            System.out.println(storeJson);
            store = List.of(mapper.readValue(storeJson, Crop[].class));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new ServerException("JSON parsing failure.");
        }
        return store;
    }

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