package org.udem.ift2255.resource;

import jakarta.inject.Inject;
import jakarta.json.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.udem.ift2255.service.EntraveService;

import java.util.List;

@Path("/entraves")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EntraveResource {

    @Inject
    EntraveService entraveService;

    @GET
    @Path("/getAllEntraves")
    public Response getAllEntraves(@QueryParam("page") @DefaultValue("1") int page,
                                   @QueryParam("limit") @DefaultValue("10") int limit) {
        try {
            JsonArray entraves = entraveService.getAllEntraves();

            int total = entraves.size();
            int start = (page - 1) * limit;
            int end = Math.min(start + limit, total);

            System.out.println("Pagination - Start: " + start + ", End: " + end + ", Total: " + total);

            if (start >= total) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Page number exceeds total available pages.")
                        .build();
            }

            List<JsonValue> paginatedEntraves = entraves.subList(start, end);

            JsonArrayBuilder resultsArray = Json.createArrayBuilder();
            for (JsonValue entrave : paginatedEntraves) {
                resultsArray.add(entrave);
            }

            JsonObject response = Json.createObjectBuilder()
                    .add("results", resultsArray)
                    .add("total", total)
                    .add("page", page)
                    .add("limit", limit)
                    .build();

            System.out.println("Paginated Response: " + response);

            return Response.ok(response).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while processing the request.")
                    .build();
        }
    }


    @GET
    @Path("/by-work-id/{workId}")
    public Response getEntravesByWorkId(@PathParam("workId") String workId) {
        List<JsonObject> entraves = entraveService.getEntravesByWorkId(workId);
        return entraves.isEmpty() ? Response.status(Response.Status.NOT_FOUND).build()
                : Response.ok(entraves).build();
    }

    @GET
    @Path("/by-street/{streetName}")
    public Response getEntravesByStreet(@PathParam("streetName") String streetName) {
        List<JsonObject> entraves = entraveService.getEntravesByStreetName(streetName);
        return entraves.isEmpty() ? Response.status(Response.Status.NOT_FOUND).build()
                : Response.ok(entraves).build();
    }
}
