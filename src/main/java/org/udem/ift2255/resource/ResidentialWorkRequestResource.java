package org.udem.ift2255.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.udem.ift2255.dto.CandidatureRequestDTO;
import org.udem.ift2255.dto.ResidentialWorkRequestDTO;
import org.udem.ift2255.service.ResidentialWorkRequestService;

import java.util.List;

@Path("/work-requests")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResidentialWorkRequestResource {

    @Inject
    ResidentialWorkRequestService workRequestService;

    // Add a candidature to a work request
    @POST
    @Path("/{id}/add-candidature")
    public Response addCandidature(@PathParam("id") Long id, CandidatureRequestDTO candidatureDTO) {
        try {
            workRequestService.addCandidature(id, candidatureDTO.getIntervenantId(), candidatureDTO.getMessage());
            return Response.ok("Candidature soumise avec succès.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    // Choose a candidature for a work request
    @POST
    @Path("/{id}/choose-candidature/{intervenantId}")
    public Response chooseCandidature(@PathParam("id") Long id, @PathParam("intervenantId") Long intervenantId) {
        try {
            workRequestService.chooseCandidature(id, intervenantId);
            return Response.ok("Candidature choisie avec succès.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/create/{residentId}")
    public Response createWorkRequest(@PathParam("residentId") Long residentId, ResidentialWorkRequestDTO requestDTO) {
        try {
            workRequestService.saveRequest(requestDTO, residentId);
            return Response.ok("Work request created successfully").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }




    // List all work requests
    @GET
    @Path("/list")
    public Response listAllWorkRequests() {
        try {
            return Response.ok(workRequestService.getAllRequests()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erreur lors du chargement des requêtes.").build();
        }
    }

    @GET
    @Path("/resident/{residentId}/requests")
    public Response getResidentWorkRequests(@PathParam("residentId") Long residentId) {
        try {
            List<ResidentialWorkRequestDTO> requests = workRequestService.getResidentWorkRequests(residentId);
            return Response.ok(requests).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur lors du chargement des requêtes.").build();
        }
    }

    // Accept a candidature for a work request
    @POST
    @Path("/{requestId}/accept-candidature/{intervenantId}")
    public Response acceptCandidature(@PathParam("requestId") Long requestId,
                                      @PathParam("intervenantId") Long intervenantId) {
        try {
            workRequestService.acceptCandidature(requestId, intervenantId);
            return Response.ok("Candidature acceptée avec succès.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erreur: " + e.getMessage()).build();
        }
    }
}
