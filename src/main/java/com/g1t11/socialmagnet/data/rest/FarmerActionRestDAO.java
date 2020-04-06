package com.g1t11.socialmagnet.data.rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.g1t11.socialmagnet.data.DatabaseException;
import com.g1t11.socialmagnet.model.farm.StealingRecord;

import org.json.JSONArray;
import org.json.JSONObject;

public class FarmerActionRestDAO extends RestDAO {
    public void plantCrop(String username, int plotNumber, String cropName) {
        JSONObject obj = new JSONObject();
        obj.put("plotNumber", plotNumber);
        obj.put("cropName", cropName);
        Entity<String> reqBody = Entity.json(obj.toString());
        Response response = getTextInvocationOfTarget(
            "farm", username, "plant").post(reqBody);
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }
    }

    public void clearPlot(String username, int plotNumber) {
        JSONObject obj = new JSONObject();
        obj.put("plotNumber", plotNumber);
        Entity<String> reqBody = Entity.json(obj.toString());
        Response response = getTextInvocationOfTarget(
            "farm", username, "clear").post(reqBody);
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }
    }

    public void harvest(String username) {
        Response response = getTextInvocationOfTarget(
            "farm", username, "harvest").post(Entity.text(""));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }
    }

    public List<StealingRecord> steal(String stealerName, String victimName) {
        Response response = getJSONInvocationOfTarget(
            "farm", stealerName, "steal", victimName).post(Entity.text(""));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }

        List<StealingRecord> stolenCrops = null;
        try {
            String stolenJson = response.readEntity(String.class);
            stolenCrops = List.of(mapper.readValue(
                    stolenJson, StealingRecord[].class));
        } catch (IOException e) {
            throw new DatabaseException("JSON parsing failure.");
        }
        return stolenCrops;
    }

    public void sendGifts(String sender, String[] recipients, String cropName) {
        JSONObject obj = new JSONObject();
        JSONArray recipientsArr = new JSONArray(recipients);
        obj.put("recipients", recipientsArr);
        obj.put("cropName", cropName);
        Entity<String> reqBody = Entity.json(obj.toString());
        Response response = getTextInvocationOfTarget(
            "farm", sender, "send").post(reqBody);
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }
    }

    public void acceptGifts(String username) {
        Response response = getTextInvocationOfTarget(
            "farm", username, "accept_gifts").post(Entity.text(""));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            throw new DatabaseException(response.readEntity(String.class));
        }
    }
}