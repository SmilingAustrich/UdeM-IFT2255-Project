package org.udem.ift2255.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.udem.ift2255.dto.LoginRequestDTO;
import org.udem.ift2255.service.AuthenticationService;

@Path("/authenticate")
public class IntervenantLoginResource {

    private final AuthenticationService authenticationService;

    // Inject the AuthenticationService
    public IntervenantLoginResource(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @POST
    @Path("/intervenant")
    @Consumes("application/json") // Accept JSON content
    public Response authenticateIntervenant(LoginRequestDTO loginRequest) {
        boolean isAuthenticated = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword(), "intervenant");

        if (isAuthenticated) {

            return Response.ok().build(); // Authentication success
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build(); // Authentication failed
        }
    }
}
