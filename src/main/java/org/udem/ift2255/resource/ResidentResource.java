package org.udem.ift2255.resource;


import jakarta.inject.Inject;
import jakarta.json.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.udem.ift2255.dto.WorkRequestDTO;
import org.udem.ift2255.model.Resident;
import org.udem.ift2255.model.TimeSlot;
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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Path("/residents")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResidentResource {

    @PersistenceContext
    EntityManager entityManager; // Inject EntityManager

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




    // Register a new resident
    @POST
    @Path("/register")
    @Transactional
    public Response registerResident(Resident resident) {
        if (Resident.find("email", resident.getEmail()).firstResult() != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Json.createObjectBuilder().add("error", "Email already registered").build())
                    .build();
        }
        resident.persist();
        return Response.status(Response.Status.CREATED)
                .entity(Json.createObjectBuilder().add("message", "Registration successful").build())
                .build();
    }

    // Login a resident
    @POST
    @Path("/login")
    public Response loginResident(@QueryParam("email") String email, @QueryParam("password") String password) {
        Resident resident = Resident.find("email", email).firstResult();
        if (resident == null || !resident.getPassword().equals(password)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Json.createObjectBuilder().add("error", "Invalid credentials").build())
                    .build();
        }
        return Response.ok(Json.createObjectBuilder().add("message", "Login successful").build())
                .build();
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

    // Fetching preferences
    @GET
    @Path("/{residentId}/preferences")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPreferences(@PathParam("residentId") Long residentId) {
        Resident resident = Resident.findById(residentId);

        if (resident == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Resident not found")
                    .build();
        }

        String preferences = resident.getPreferredHours();
        if (preferences == null || preferences.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build(); // Return 204 if no preferences
        }

        try {
            // Parse the preferences string as JSON array
            JsonArray preferencesArray = Json.createReader(new StringReader(preferences)).readArray();
            List<TimeSlot> timeSlots = new ArrayList<>();

            for (JsonObject preference : preferencesArray.getValuesAs(JsonObject.class)) {
                String day = preference.getString("day");
                String startTime = preference.getString("startTime");
                String endTime = preference.getString("endTime");
                timeSlots.add(new TimeSlot(day, startTime, endTime));
            }

            return Response.ok(timeSlots).build(); // Return the list of time slots
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error parsing preferences: " + e.getMessage())
                    .build();
        }
    }

    // Deleting a specific preference
    @DELETE
    @Path("/{residentId}/preferences")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public Response deletePreference(@PathParam("residentId") Long residentId, TimeSlot preferenceToDelete) {
        Resident resident = Resident.findById(residentId);

        if (resident == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Resident not found").build();
        }

        String preferences = resident.getPreferredHours();
        if (preferences == null || preferences.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity("No preferences to delete").build();
        }

        try {
            // Parse the preferences JSON
            JsonArray preferencesArray = Json.createReader(new StringReader(preferences)).readArray();
            JsonArrayBuilder updatedArrayBuilder = Json.createArrayBuilder();

            // Filter out the preference to delete
            for (JsonObject preference : preferencesArray.getValuesAs(JsonObject.class)) {
                String day = preference.getString("day");
                String startTime = preference.getString("startTime");
                String endTime = preference.getString("endTime");

                // Only add preferences that do not match the one to delete
                if (!(day.equals(preferenceToDelete.getDay()) &&
                        startTime.equals(preferenceToDelete.getStartTime()) &&
                        endTime.equals(preferenceToDelete.getEndTime()))) {
                    updatedArrayBuilder.add(preference);
                }
            }

            JsonArray updatedArray = updatedArrayBuilder.build();
            if (updatedArray.isEmpty()) {
                resident.setPreferredHours(null); // No preferences left
            } else {
                resident.setPreferredHours(updatedArray.toString());
            }

            resident.persist();  // Ensure updated preferences are persisted

            return Response.ok("Preference deleted successfully").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting preference: " + e.getMessage())
                    .build();
        }
    }

    // Saving or updating preferences
    @PATCH
    @Path("/{residentId}/preferences")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response savePreferences(@PathParam("residentId") Long residentId, List<TimeSlot> timeSlots) {
        Resident resident = Resident.findById(residentId);

        if (resident == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Resident not found").build();
        }

        // Convert the timeSlots list to a JSON array
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (TimeSlot slot : timeSlots) {
            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                    .add("day", slot.getDay())
                    .add("startTime", slot.getStartTime())
                    .add("endTime", slot.getEndTime());
            arrayBuilder.add(objBuilder);
        }

        JsonArray jsonArray = arrayBuilder.build();

        // Save preferences as a JSON string
        resident.setPreferredHours(jsonArray.toString());
        resident.persist();  // Persist the changes

        return Response.ok("Preferences updated successfully").build();
    }



}






