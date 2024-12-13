package org.udem.ift2255.resource;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.udem.ift2255.dto.WorkRequestDTO;
import org.udem.ift2255.model.Resident;
import org.udem.ift2255.service.AuthenticationService;
import org.udem.ift2255.service.ResidentService;
import jakarta.transaction.Transactional;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import org.udem.ift2255.service.ResidentService;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;




@Path("/residents")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResidentResource {

    @Inject
    ResidentService residentService;

    @Inject
    AuthenticationService authenticationService;  // Inject AuthenticationService

    @POST
    @Path("/{residentId}/submitRequest")
    @Consumes("application/json")
    @Produces("application/json")
    public Response soumettreRequeteTravail(@PathParam("residentId") Long residentId, WorkRequestDTO requestDTO) {
        try {
            // Retrieve the Resident by residentId
            Resident resident = Resident.findById(residentId);

            if (resident == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Resident not found with id: " + residentId)
                        .build();
            }

            // Delegate the request submission to ResidentService
            residentService.soumettreRequeteTravail(resident, requestDTO.getWorkTitle(),
                    requestDTO.getDetailedWorkDescription(), requestDTO.getQuartier(),
                    requestDTO.getWorkType(), requestDTO.getWorkWishedStartDate());

            return Response.status(Response.Status.CREATED).entity("Requête soumise avec succès.").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/authenticate")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateResident(@FormParam("email") String email, @FormParam("password") String password) {
        // Use AuthenticationService to authenticate with "resident" as userType
        boolean isAuthenticated = authenticationService.authenticate(email, password, "resident");

        if (isAuthenticated) {
            return Response.ok().entity("Resident authenticated successfully").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
        }
    }



    @POST
    @Path("/register")
    @Transactional
    public Response registerResident(Resident resident) {
        if (Resident.find("email", resident.getEmail()).firstResult() != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Email already registered").build();
        }
        resident.persist();
        return Response.status(Response.Status.CREATED).entity("Registration successful").build();
    }

    @POST
    @Path("/login")
    public Response loginResident(@QueryParam("email") String email, @QueryParam("password") String password) {
        Resident resident = Resident.find("email", email).firstResult();
        if (resident == null || !resident.getPassword().equals(password)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
        }
        return Response.ok("Login successful").build();
    }





    // New endpoint to fetch all Entraves (no need for residentId)
    @GET
    @Path("/entraves")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consulterEntraves() {
        try {
            // Fetch the entraves using ResidentService
            JsonArray entraves = residentService.consulterEntraves();

            // Check if the entraves were found
            if (entraves == null || entraves.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Aucune entrave trouvée.")
                        .build();
            }

            // Return the response with the fetched data
            return Response.ok(entraves).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur lors de la récupération des entraves: " + e.getMessage())
                    .build();
        }
    }



}
