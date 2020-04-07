package com.g1t11.socialmagnet.data.rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.g1t11.socialmagnet.data.ServerException;
import com.g1t11.socialmagnet.model.farm.StealingRecord;

import org.json.JSONArray;
import org.json.JSONObject;

public class FarmerActionRestDAO extends RestDAO {
    /**
     * Plant a specified crop on a specified plot of a given farmer in the
     * database.
     * <p>
     * The farmer's inventory will have the corresponding crop deducted.
     * @param username The username of the farmer planting the crop.
     * @param plotNumber The number of the plot to plant the crop in.
     * @param cropName The name of the crop to plant.
     * @throws ServerException If the inventory is insufficient.
     */
    public void plantCrop(String username, int plotNumber, String cropName) {
        JSONObject obj = new JSONObject();
        obj.put("plotNumber", plotNumber);
        obj.put("cropName", cropName);
        Entity<String> reqBody = Entity.json(obj.toString());
        Response response = getTextInvocationOfTarget(
            "farm", username, "plant").post(reqBody);
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }
    }

    /**
     * Clears a specified plot of a given user in the database.
     * <p>
     * If the crop is wilted, then 5 gold will be deducted from the farmer's
     * wealth.
     * @param username The username of the farmer planting the crop.
     * @param plotNumber The number of the plot to clear the crop from.
     * @throws ServerException If there is insufficient wealth.
     */
    public void clearPlot(String username, int plotNumber) {
        JSONObject obj = new JSONObject();
        obj.put("plotNumber", plotNumber);
        Entity<String> reqBody = Entity.json(obj.toString());
        Response response = getTextInvocationOfTarget(
            "farm", username, "clear").post(reqBody);
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }
    }

    /**
     * Harvests all ready crops from all plots of a farmer on the database.
     * <p>
     * The generated gold and XP will be added to the farmer's wealth and XP.
     * <p>
     * Any record of previous stealers of harvested plots will be cleared,
     * allowing others to steal from the plot again should another crop be
     * ready to harvest.
     * @param username The username of the farmer harvesting crops.
     */
    public void harvest(String username) {
        Response response = getTextInvocationOfTarget(
            "farm", username, "harvest").post(Entity.text(""));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }
    }

    /**
     * Steal harvestable crops from another user.
     * <p>
     * All stolen yields are generated on the server.
     * <p>
     * One user is allowed to steal a random integer 1-5% of crops from a plot,
     * and one plot can only have a maximum of 20% of crops stolen from it by
     * all users.
     * <p>
     * The generated gold and XP will be added to the stealer's wealth and XP.
     * <p>
     * Once stolen from, a plot cannot be stolen from again by the same user
     * until it is replanted.
     * @param stealerName The username of the farmer stealing.
     * @param victimName The username of the farmer being stolen from.
     * @return A list of what was stolen, in the form of {@link StealingRecord}.
     */
    public List<StealingRecord> steal(String stealerName, String victimName) {
        Response response = getJSONInvocationOfTarget(
            "farm", stealerName, "steal", victimName).post(Entity.text(""));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }

        List<StealingRecord> stolenCrops = null;
        try {
            String stolenJson = response.readEntity(String.class);
            stolenCrops = List.of(mapper.readValue(
                    stolenJson, StealingRecord[].class));
        } catch (IOException e) {
            throw new ServerException("JSON parsing failure.");
        }
        return stolenCrops;
    }

    /**
     * Send a gift at no cost to a friend.
     * @param sender The username of the farmer sending the gift.
     * @param recipients An array of usernames of recipients to send to.
     * @param cropName The crop to send.
     * @throws ServerException If any recipient already received a gift from the
     * sender today.
     */
    public void sendGifts(String sender, String[] recipients, String cropName) {
        JSONObject obj = new JSONObject();
        JSONArray recipientsArr = new JSONArray(recipients);
        obj.put("recipients", recipientsArr);
        obj.put("cropName", cropName);
        Entity<String> reqBody = Entity.json(obj.toString());
        Response response = getTextInvocationOfTarget(
            "farm", sender, "send").post(reqBody);
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }
    }

    /**
     * Accept all pending gifts.
     * @param username The username of the user accepting gifts.
     */
    public void acceptGifts(String username) {
        Response response = getTextInvocationOfTarget(
            "farm", username, "accept_gifts").post(Entity.text(""));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new ServerException(response.readEntity(String.class));
        }
    }
}