package org.udem.ift2255.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.udem.ift2255.service.EntraveService;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import java.util.List;

@Path("/entraves")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EntraveResource {

    @Inject
    EntraveService entraveService;

    @GET
    public Response getAllEntraves() {
        JsonArray entraves = entraveService.getAllEntraves();
        return Response.ok(entraves).build();
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
