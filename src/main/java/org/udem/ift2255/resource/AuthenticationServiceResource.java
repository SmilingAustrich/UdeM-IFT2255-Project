package org.udem.ift2255.resource;

import org.udem.ift2255.service.AuthenticationService;
import org.udem.ift2255.dto.LoginRequestDTO;
import org.udem.ift2255.model.*;
import org.udem.ift2255.repository.ResidentRepository;
import org.udem.ift2255.repository.IntervenantRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;

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
     * Hash password using BCrypt
     */
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Compare password with the hashed password
     */
    private boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    /**
     * Authenticate Resident using provided email and password
     */
    @POST
    @Path("/resident")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateResident(LoginRequestDTO loginRequest) {
        boolean isAuthenticated = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword(), "resident");

        if (isAuthenticated) {
            return Response.ok().entity("Resident authenticated successfully").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid credentials").build();
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
        boolean isAuthenticated = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword(), "intervenant");

        if (isAuthenticated) {
            return Response.ok().entity("Intervenant authenticated successfully").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid credentials").build();
        }
    }

    /**
     * Sign up a new Resident
     */
    @POST
    @Path("/signup/resident")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signUpResident(Resident resident) {
        if (residentRepository.find("email", resident.getEmail()).firstResult() != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("A resident with this email already exists.")
                    .build();
        }

        // Hash password before saving
        resident.setPassword(hashPassword(resident.getPassword()));
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
        if (intervenantRepository.find("email", intervenant.getEmail()).firstResult() != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("An intervenant with this email already exists.")
                    .build();
        }

        // Hash password before saving
        intervenant.setPassword(hashPassword(intervenant.getPassword()));
        intervenantRepository.persist(intervenant);

        return Response.status(Response.Status.CREATED)
                .entity("Intervenant registered successfully")
                .build();
    }
}
