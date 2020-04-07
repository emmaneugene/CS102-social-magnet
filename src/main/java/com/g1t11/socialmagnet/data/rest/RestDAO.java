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
    /**
     * The location of the Social Magnet Web Service.
     * <p>
     * This value is injected when the application starts.
     */
    public static String BASE_URL;

    /**
     * A REST client that handles requests to and responses from the web
     * service.
     */
    JerseyClient client;

    /**
     * A JSON-to-POJO parser, used to map JSON data directly onto models.
     */
    ObjectMapper mapper;

    /**
     * Configures the REST client and JSON-to-POJO parser.
     */
    public RestDAO() {
        ClientConfig config = new ClientConfig();
        client = JerseyClientBuilder.createClient(config);
        mapper = new ObjectMapper();
    }

    /**
     * A convenience method to get a builder for an invocation of some web
     * resource located in a subdirectory of the web service, in JSON format.
     * <p>
     * It is very common to simply access some web resource, and this method
     * allows us to minimise repeating the same procedures.
     * @param urlParts An array of the URL components of a given resource. Given
     * a resource at the location
     * <code>http://.../magnet/user/adam/friends</code>, this method would
     * simply be invoked with <code>urlParts = {"user", "adam",
     * "friends"}</code>.
     * @return A builder for an invocation to a web resource, in JSON format.
     */
    protected Invocation.Builder getJSONInvocationOfTarget(String ...urlParts) {
        return getTarget(urlParts).request(MediaType.APPLICATION_JSON);
    }

    /**
     * A convenience method to get a builder for an invocation of some web
     * resource located in a subdirectory of the web service, in plain text
     * format.
     * <p>
     * It is very common to simply access some web resource, and this method
     * allows us to minimise repeating the same procedures.
     * @param urlParts An array of the URL components of a given resource. Given
     * a resource at the location
     * <code>http://.../magnet/user/adam/friends</code>, this method would
     * simply be invoked with <code>urlParts = {"user", "adam",
     * "friends"}</code>.
     * @return A builder for an invocation to a web resource, in plain text
     * format.
     */
    protected Invocation.Builder getTextInvocationOfTarget(String ...urlParts) {
        return getTarget(urlParts).request(MediaType.TEXT_PLAIN);
    }

    /**
     * Create a target that references a web resource at a given subdirectory.
     * @param urlParts An array of the URL components of a given resource. Given
     * a resource at the location
     * <code>http://.../magnet/user/adam/friends</code>, this method would
     * simply be invoked with <code>urlParts = {"user", "adam",
     * "friends"}</code>.
     * @return A target that references a web resource at the given
     * subdirectory.
     */
    protected WebTarget getTarget(String ...urlParts) {
        return client.target(BASE_URL + "/" + String.join("/", urlParts));
    }
}