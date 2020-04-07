package com.g1t11.socialmagnet.data.rest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.g1t11.socialmagnet.data.ServerException;
import com.g1t11.socialmagnet.data.ServerException.ErrorCode;
import com.g1t11.socialmagnet.model.farm.Farmer;
import com.g1t11.socialmagnet.model.farm.Plot;

public class FarmerLoadRestDAO extends RestDAO {
    /**
     * Get farmer details of a given user.
     * <p>
     * This allows us to minimise unnecessarily loading farmer information when
     * it is not required.
     * @param username The username of the farmer.
     * @return A unique farmer with the specified username.
     */
    public Farmer getFarmer(String username) {
        Response response = getJSONInvocationOfTarget(
                "farm", username).get();
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException(ErrorCode.USER_NOT_FOUND);
        }

        Farmer farmer = null;
        try {
            String farmerJson = response.readEntity(String.class);
            farmer = mapper.readValue(farmerJson, Farmer.class);
        } catch (IOException e) {
            throw new ServerException("JSON parsing failure.");
        }
        return farmer;
    }

    /**
     * Get the inventory of a farmer.
     * @param username The username of the farmer whose inventory to access.
     * @return A sorted map of the names of crops in an inventory with its
     * corresponding quantities.
     */
    public Map<String, Integer> getInventoryCrops(String username) {
        Response response = getJSONInvocationOfTarget(
                "farm", username, "inventory").get();
        if (response.getStatus() != Status.OK.getStatusCode())  {
            throw new ServerException(response.readEntity(String.class));
        }

        Map<String, Integer> inv = null;
        try {
            String invJson = response.readEntity(String.class);
            inv = mapper.readValue(invJson,
                    new TypeReference<Map<String, Integer>>() {});
        } catch (IOException e) {
            throw new ServerException("JSON parsing failure.");
        }

        return inv;
    }

    /**
     * Get the plots of a farmer.
     * @param username The username of the farmer whose plots to retrieve.
     * @param maxPlotCount The maximum number of plots the farmer can access.
     * @return The plots of a farmer.
     */
    public List<Plot> getPlots(String username, int maxPlotCount) {
        Response response = getTarget("farm", username, "plots")
                .queryParam("count", maxPlotCount)
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }

        List<Plot> plots = null;
        try {
            String plotsJson = response.readEntity(String.class);
            plots = List.of(mapper.readValue(plotsJson, Plot[].class));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new ServerException("JSON parsing failure.");
        }

        return plots;
    }

    /**
     * Get the number of gifts sent by the user today.
     * @param username The username of the gift sender.
     * @return The number of gifts sent today.
     */
    public int getGiftCountToday(String username) {
        Response response = getTextInvocationOfTarget(
                "farm", username, "gift_count_today").get();
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }
        try {
            String countStr = response.readEntity(String.class);
            return Integer.parseInt(countStr);
        } catch (NumberFormatException e) {
            throw new ServerException("Integer parsing failure.");
        }
    }

    /**
     * For a given user, get a map of usernames to send to and whether gifts
     * were already sent today.
     * @param username The username of the sender.
     * @param recipients An array of users to check.
     * @return A map of recipient usernames to whether a gift was already sent
     * today.
     */
    public Map<String, Boolean> sentGiftToUsersToday(
                String username, String[] recipients) {
        Object[] recipientsObj = recipients;
        Response response =
                getTarget("farm", username, "sent_gift_to_users_today")
                .queryParam("recipients", recipientsObj)
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }

        Map<String, Boolean> sentMap = null;
        try {
            String sentMapJson = response.readEntity(String.class);
            sentMap = mapper.readValue(sentMapJson,
                    new TypeReference<Map<String, Boolean>>() {});
        } catch (IOException e) {
            throw new ServerException("JSON parsing failure.");
        }

        return sentMap;
    }
}