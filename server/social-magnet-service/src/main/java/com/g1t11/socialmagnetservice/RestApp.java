package com.g1t11.socialmagnetservice;

import org.glassfish.jersey.server.ResourceConfig;

public class RestApp extends ResourceConfig {
    public RestApp() {
        packages("com.g1t11.socialmagnetservice.rest");
        // register(AuthenticationFilter.class);
    }
}