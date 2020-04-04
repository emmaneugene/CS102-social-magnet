package com.g1t11.socialmagnetservice;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.g1t11.socialmagnetservice.data.CredentialsDAO;

public class AuthenticationFilter implements ContainerRequestFilter {
    @Context
    private ResourceInfo resourceInfo;

    private static final String AUTHORIZATION_PROP = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";

    @Override
    public void filter(ContainerRequestContext requestContext)
            throws IOException {
        Method method = resourceInfo.getResourceMethod();

        // Method access allowed for all
        if (method.isAnnotationPresent(PermitAll.class)) {
            return;
        }

        // Method access denied for all
        if (method.isAnnotationPresent(DenyAll.class)) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("Access blocked for all users.").build());
            return;
        }

        // Get request headers
        final MultivaluedMap<String, String> headers
                = requestContext.getHeaders();

        // Fetch authorization header
        final List<String> authorization = headers.get(AUTHORIZATION_PROP);

        if (authorization == null || authorization.isEmpty()) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Unauthorized access to resource.").build());
            return;
        }

        // Get encoded username and password
        final String encodedUserPassword = authorization.get(0)
                .replaceFirst(AUTHENTICATION_SCHEME + " ", "");
        // Decode encoded usename and password
        String usernamePassword = new String(
                Base64.getDecoder().decode(encodedUserPassword));
        final String[] tokens = usernamePassword.split(":");
        final String username = tokens[0];
        final String password = tokens[1];

        // Verify user access
        boolean isValid = CredentialsDAO.login(username, password);
        if (!isValid) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid authorization.").build());
            return;
        }

        // if (method.isAnnotationPresent(RolesAllowed.class)) {
        //     RolesAllowed rolesAnnotation
        //             = method.getAnnotation(RolesAllowed.class);
        //     Set<String> rolesSet
        //             = new HashSet<>(Arrays.asList(rolesAnnotation.value()));
        //     if (!rolesSet.contains("USER")) {
        //     }
        // }
    }
}