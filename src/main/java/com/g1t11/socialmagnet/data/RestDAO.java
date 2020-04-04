package com.g1t11.socialmagnet.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class RestDAO {
    private final static String BASE_URL
            = "http://localhost:8080/social-magnet-service/";

    Client client;

    ObjectMapper mapper;

    public RestDAO() {
        client = ClientBuilder.newClient();
        mapper = new ObjectMapper();
    }

    protected Invocation.Builder getJSONInvocationOfTarget(String ...urlParts) {
        return getTarget(urlParts).request(MediaType.APPLICATION_JSON);
    }

    protected Invocation.Builder getTextInvocationOfTarget(String ...urlParts) {
        return getTarget(urlParts).request(MediaType.TEXT_PLAIN);
    }

    private WebTarget getTarget(String ...urlParts) {
        return client.target(BASE_URL + String.join("/", urlParts));
    }
}