package org.udem.ift2255.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.udem.ift2255.service.AuthenticationService;
import jakarta.ws.rs.FormParam;

@Path("/authenticate")
public class ResidentLoginResource {

    @Inject
    AuthenticationService authenticationService; // Inject AuthenticationService here

    // Handle the POST request to authenticate resident
    @POST
    @Path("/resident")
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateResident(@FormParam("email") String email, @FormParam("password") String password) {
        // Use the injected authenticationService to call the authenticate method
        boolean isAuthenticated = authenticationService.authenticate(email, password, "resident");

        if (isAuthenticated) {
            return Response.ok().entity("Resident authenticated successfully").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid credentials").build();
        }
    }
}
