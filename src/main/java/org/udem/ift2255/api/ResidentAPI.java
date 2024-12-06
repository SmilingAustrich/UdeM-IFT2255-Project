package org.udem.ift2255.api;

import com.google.gson.Gson;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import com.google.gson.JsonArray;
import org.udem.ift2255.service.ResidentService;

import java.util.List;
import java.util.Map;

@Path("/api")  // Set the base path for all endpoints
public class ResidentAPI {

    // Use the ResidentService to fetch entraves data
    private ResidentService residentService = new ResidentService();

    @GET
    @Path("/entraves")
    @Produces("application/json")
    public Response consulterEntraves() {
        try {
            // Fetch the entraves using ResidentService
            JsonArray entravesJsonArray = residentService.consulterEntraves();

            // Convert JsonArray to List<Map<String, Object>> for Jackson compatibility
            Gson gson = new Gson();
            List<Map<String, Object>> entravesList = gson.fromJson(entravesJsonArray, List.class);

            // Return the response with the fetched data
            return Response.ok(entravesList).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur lors de la récupération des entraves: " + e.getMessage())
                    .build();
        }
    }
}
