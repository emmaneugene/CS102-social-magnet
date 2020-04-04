package com.g1t11.socialmagnet.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class RestDAO {
    private final static String BASE_URL
            = "http://localhost:8080/social-magnet-service/";

    Client client;

    ObjectMapper mapper = new ObjectMapper();

    public RestDAO() {
        client = ClientBuilder.newClient();
    }

    protected WebTarget getTarget(String ...urlParts) {
        return client.target(BASE_URL + String.join("/", urlParts));
    }
}