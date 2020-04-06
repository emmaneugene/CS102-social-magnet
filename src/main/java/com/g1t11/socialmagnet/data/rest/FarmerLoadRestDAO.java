package com.g1t11.socialmagnet.data.rest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.g1t11.socialmagnet.data.DatabaseException;
import com.g1t11.socialmagnet.data.DatabaseException.SQLErrorCode;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.farm.Plot;

public class FarmerLoadRestDAO extends RestDAO {
    public Farmer getFarmer(String username) {
        Response response = getJSONInvocationOfTarget(
                "farm", username).get();
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new DatabaseException(SQLErrorCode.USER_NOT_FOUND);
        }

        Farmer farmer = null;
        try {
            String farmerJson = response.readEntity(String.class);
            farmer = mapper.readValue(farmerJson, Farmer.class);
        } catch (IOException e) {
            throw new DatabaseException("JSON parsing failure.");
        }
        return farmer;
    }

    public Map<String, Integer> getInventoryCrops(String username) {
        Response response = getJSONInvocationOfTarget(
                "farm", username, "inventory").get();
        if (response.getStatus() != Status.OK.getStatusCode())  {
            throw new DatabaseException(response.readEntity(String.class));
        }

        Map<String, Integer> inv = null;
        try {
            String invJson = response.readEntity(String.class);
            inv = mapper.readValue(invJson,
                    new TypeReference<Map<String, Integer>>() {});
        } catch (IOException e) {
            throw new DatabaseException("JSON parsing failure.");
        }

        return inv;
    }

    public List<Plot> getPlots(String username, int maxPlotCount) {
        Response response = getTarget("farm", username, "plots")
                .queryParam("count", maxPlotCount)
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }

        List<Plot> plots = null;
        try {
            String plotsJson = response.readEntity(String.class);
            plots = List.of(mapper.readValue(plotsJson, Plot[].class));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new DatabaseException("JSON parsing failure.");
        }

        return plots;
    }

    public int getGiftCountToday(String username) {
        Response response = getInvocationOfTarget(
                "farm", username, "gift_count_today").get();
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }
        try {
            String countStr = response.readEntity(String.class);
            return Integer.parseInt(countStr);
        } catch (NumberFormatException e) {
            throw new DatabaseException("Integer parsing failure.");
        }
    }

    public Map<String, Boolean> sentGiftToUsersToday(
                String username, String[] recipients) {
        Object[] recipientsObj = recipients;
        Response response =
                getTarget("farm", username, "sent_gift_to_users_today")
                .queryParam("recipients", recipientsObj)
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }

        Map<String, Boolean> sentMap = null;
        try {
            String sentMapJson = response.readEntity(String.class);
            sentMap = mapper.readValue(sentMapJson,
                    new TypeReference<Map<String, Boolean>>() {});
        } catch (IOException e) {
            throw new DatabaseException("JSON parsing failure.");
        }

        return sentMap;
    }
}