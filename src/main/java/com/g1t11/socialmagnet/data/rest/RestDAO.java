package com.g1t11.socialmagnet.data.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.glassfish.jersey.client.ClientConfig;
// import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class RestDAO {
    private final static String BASE_URL
            = "http://localhost:8080/social-magnet-service/";

    JerseyClient client;

    ObjectMapper mapper;

    public RestDAO() {
        ClientConfig config = new ClientConfig();
        // config.property(
        //         ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true);
        client = JerseyClientBuilder.createClient(config);
        mapper = new ObjectMapper();
    }

    protected Invocation.Builder getJSONInvocationOfTarget(String ...urlParts) {
        return getTarget(urlParts).request(MediaType.APPLICATION_JSON);
    }

    protected Invocation.Builder getTextInvocationOfTarget(String ...urlParts) {
        return getTarget(urlParts).request(MediaType.TEXT_PLAIN);
    }

    protected Invocation.Builder getInvocationOfTarget(String ...urlParts) {
        return getTarget(urlParts).request();
    }

    private WebTarget getTarget(String ...urlParts) {
        return client.target(BASE_URL + String.join("/", urlParts));
    }
}