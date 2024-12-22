package org.udem.ift2255.resource;

import jakarta.json.Json;
import org.udem.ift2255.service.AuthenticationService;
import org.udem.ift2255.dto.LoginRequestDTO;
import org.udem.ift2255.model.*;
import org.udem.ift2255.repository.ResidentRepository;
import org.udem.ift2255.repository.IntervenantRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("/authenticate")
public class AuthenticationServiceResource {

    @Inject
    ResidentRepository residentRepository;

    @Inject
    IntervenantRepository intervenantRepository;

    @Inject
    AuthenticationService authenticationService;

    /**
     * Authenticate Resident using provided email and password
     */
    @POST
    @Path("/resident")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateResident(LoginRequestDTO loginRequest) {
        Resident resident = residentRepository.find("email", loginRequest.getEmail()).firstResult();

        // Verify password without encryption (plain text comparison)
        if (resident != null && loginRequest.getPassword().equals(resident.getPassword())) {
            // Return resident information including residentId and email
            return Response.ok()
                    .entity(Json.createObjectBuilder()
                            .add("residentId", resident.id) // Ensure residentId is included
                            .add("email", resident.getEmail())   // Ensure email is included
                            .build())
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid credentials")
                    .build();
        }
    }


    /**
     * Authenticate Intervenant using provided email and password
     */
    @POST
    @Path("/intervenant")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateIntervenant(LoginRequestDTO loginRequest) {
        // Find the intervenant by email
        Intervenant intervenant = intervenantRepository.find("email", loginRequest.getEmail()).firstResult();

        // Check if the intervenant exists and if the password matches
        if (intervenant != null && loginRequest.getPassword().equals(intervenant.getPassword())) {
            // Return the intervenantId and email in the response
            return Response.ok()
                    .entity(Json.createObjectBuilder()
                            .add("message", "Intervenant authenticated successfully")
                            .add("intervenantId", intervenant.id)  // Include the intervenantId
                            .add("email", intervenant.getEmail())  // Include the email
                            .build())
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Json.createObjectBuilder()
                            .add("message", "Invalid credentials")
                            .build())
                    .build();
        }
    }



    /**
     * Sign up a new Resident
     */
    @POST
    @Path("/signup/resident")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signUpResident(Resident resident) {
        // Check if the email already exists in the database
        if (residentRepository.find("email", resident.getEmail()).firstResult() != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("A resident with this email already exists.")
                    .build();
        }

        // Directly save the plain text password (no encryption for simplicity)
        residentRepository.persist(resident);

        return Response.status(Response.Status.CREATED)
                .entity("Resident registered successfully")
                .build();
    }

    /**
     * Sign up a new Intervenant
     */
    @POST
    @Path("/signup/intervenant")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signUpIntervenant(Intervenant intervenant) {
        // Check if the email already exists in the database
        if (intervenantRepository.find("email", intervenant.getEmail()).firstResult() != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Json.createObjectBuilder()
                            .add("message", "An intervenant with this email already exists.")
                            .build())
                    .build();
        }

        // Directly save the plain text password (no encryption for simplicity)
        intervenantRepository.persist(intervenant);

        return Response.status(Response.Status.CREATED)
                .entity(Json.createObjectBuilder()
                        .add("message", "Intervenant registered successfully")
                        .build())
                .build();
    }


}
