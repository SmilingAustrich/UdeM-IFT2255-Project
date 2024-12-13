package org.udem.ift2255.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import org.udem.ift2255.service.ResidentService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

            // Convert JsonArray to List<Map<String, Object>>
            List<Map<String, Object>> entravesList = entravesJsonArray.stream()
                    .filter(jsonValue -> jsonValue.getValueType() == JsonValue.ValueType.OBJECT) // Ensure it's a JsonObject
                    .map(jsonValue -> (JsonObject) jsonValue) // Cast to JsonObject
                    .map(jsonObject -> jsonObject.entrySet().stream()
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey,
                                    entry -> parseJsonValue(entry.getValue()) // Convert JsonValue to Java Object
                            )))
                    .collect(Collectors.toList());

            // Return the response with the fetched data
            return Response.ok(entravesList).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur lors de la récupération des entraves: " + e.getMessage())
                    .build();
        }
    }

    // Helper method to parse JsonValue into a Java Object
    private Object parseJsonValue(JsonValue value) {
        switch (value.getValueType()) {
            case STRING:
                return value.toString().replace("\"", ""); // Remove quotes for strings
            case NUMBER:
                return value.toString(); // Return as a String (or convert to a number if needed)
            case TRUE:
                return true;
            case FALSE:
                return false;
            case NULL:
                return null;
            case OBJECT:
                return value; // Return JsonObject for complex objects
            case ARRAY:
                return value; // Return JsonArray for complex arrays
            default:
                throw new IllegalArgumentException("Unsupported JSON value type: " + value.getValueType());
        }
    }
}
