package org.udem.ift2255.resource;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import org.udem.ift2255.service.ResidentService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("/travaux")
public class TravauxResource {

    @Inject
    ResidentService residentService;

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response rechercherTravaux(
            @QueryParam("criteria") String criteria,
            @QueryParam("value") String value,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("limit") @DefaultValue("10") int limit
    ) {
        try {
            // Validate criteria and value
            if (criteria == null || criteria.isBlank() || value == null || value.isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Missing or invalid criteria or value")
                        .build();
            }

            // Fetch travaux based on criteria and value
            List<Map<String, String>> travaux = fetchFilteredTravaux(criteria, value);

            // Paginate results
            List<Map<String, String>> paginatedResults = paginateResults(travaux, page, limit);

            // Build JSON response
            JsonObject response = buildResponse(paginatedResults, travaux.size(), page, limit);

            return Response.ok(response).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while processing the request.")
                    .build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTravaux(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("limit") @DefaultValue("10") int limit
    ) {
        try {
            List<Map<String, String>> travaux = fetchAllTravaux();
            List<Map<String, String>> paginatedResults = paginateResults(travaux, page, limit);
            JsonObject response = buildResponse(paginatedResults, travaux.size(), page, limit);

            return Response.ok(response).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while processing the request.")
                    .build();
        }
    }


    private List<Map<String, String>> fetchFilteredTravaux(String criteria, String value) {
        List<Map<String, String>> travaux = fetchAllTravaux(); // Fetch all data first

        // Filter the data based on criteria and value
        return travaux.stream()
                .filter(travail -> travail.get(criteria).toLowerCase().contains(value.toLowerCase()))
                .toList();
    }

    private List<Map<String, String>> fetchAllTravaux() {
        List<Map<String, String>> travaux = new ArrayList<>();

        try {
            // Fetch data from the Montreal API
            URL url = new URL("https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Parse the JSON response
                    JsonObject jsonResponse = Json.createReader(new StringReader(response.toString())).readObject();
                    JsonArray records = jsonResponse.getJsonObject("result").getJsonArray("records");

                    // Map the API fields to the expected keys
                    for (JsonObject record : records.getValuesAs(JsonObject.class)) {
                        travaux.add(Map.of(
                                "id", record.getString("id", "N/A"), // Identifiant du travail
                                "boroughid", record.getString("boroughid", "N/A"), // Arrondissement
                                "currentstatus", record.getString("currentstatus", "N/A"), // Statut actuel du travail
                                "reason_category", record.getString("reason_category", "N/A"), // Motif du travail
                                "submittercategory", record.getString("submittercategory", "N/A"), // Catégorie d'intervenant
                                "organizationname", record.getString("organizationname", "N/A") // Nom de l'intervenant
                        ));
                    }
                }
            } else {
                throw new Exception("Failed to fetch data. HTTP Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // You can return empty data or dummy data in case of an error
            travaux.add(Map.of("id", "Error", "boroughid", "Error", "currentstatus", "Error", "reason_category", "Error", "submittercategory", "Error", "organizationname", "Error"));
        }

        return travaux;
    }

    private List<Map<String, String>> paginateResults(List<Map<String, String>> travaux, int page, int limit) {
        int start = (page - 1) * limit;
        int end = Math.min(start + limit, travaux.size());
        return travaux.subList(start, end);
    }

    private JsonObject buildResponse(List<Map<String, String>> travaux, int total, int page, int limit) {
        JsonArrayBuilder resultsArray = Json.createArrayBuilder();
        for (Map<String, String> travail : travaux) {
            JsonObject travailJson = Json.createObjectBuilder()
                    .add("id", travail.get("id")) // Identifiant du travail
                    .add("boroughid", travail.get("boroughid")) // Arrondissement
                    .add("currentstatus", travail.get("currentstatus")) // Statut actuel du travail
                    .add("reason_category", travail.get("reason_category")) // Motif du travail
                    .add("submittercategory", travail.get("submittercategory")) // Catégorie d'intervenant
                    .add("organizationname", travail.get("organizationname")) // Nom de l'intervenant
                    .build();
            resultsArray.add(travailJson);
        }

        return Json.createObjectBuilder()
                .add("results", resultsArray)
                .add("total", total)
                .add("page", page)
                .add("limit", limit)
                .build();
    }
}
