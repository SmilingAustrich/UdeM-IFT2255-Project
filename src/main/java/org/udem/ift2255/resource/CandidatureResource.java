package org.udem.ift2255.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.udem.ift2255.dto.CandidatureRequestDTO;
import org.udem.ift2255.model.Candidature;
import org.udem.ift2255.model.Intervenant;
import org.udem.ift2255.model.ResidentialWorkRequest;
import org.udem.ift2255.service.CandidatureService;

import java.util.List;
import java.util.stream.Collectors;

@Path("/candidatures")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CandidatureResource {

    @Inject
    CandidatureService candidatureService;

    // Submit candidature for an intervenant
    @POST
    @Path("/{intervenantId}")
    public Response submitCandidature(@PathParam("intervenantId") Long intervenantId, CandidatureRequestDTO candidatureRequest) {
        try {
            // Find the intervenant by the provided ID
            Intervenant intervenant = Intervenant.findById(intervenantId);  // This should fetch the right intervenant
            if (intervenant == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"success\": false, \"message\": \"Intervenant not found.\"}")
                        .build();
            }

            // Now you can safely proceed with fetching the work request
            ResidentialWorkRequest workRequest = ResidentialWorkRequest.findById(candidatureRequest.getWorkRequestId());
            if (workRequest == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"success\": false, \"message\": \"Work request not found.\"}")
                        .build();
            }

            // Proceed with creating the candidature
            Candidature candidature = new Candidature(intervenant, candidatureRequest.getMessage(), workRequest);
            candidatureService.saveCandidature(candidature);

            return Response.status(Response.Status.OK)
                    .entity("{\"success\": true}")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"success\": false, \"message\": \"Une erreur interne est survenue\"}")
                    .build();
        }
    }


    // Get all candidatures for a specific intervenant
    @GET
    @Path("/{intervenantId}")
    public Response getAllCandidatures(@PathParam("intervenantId") Long intervenantId) {
        try {
            // Fetch candidatures for the given intervenantId
            List<Candidature> candidatures = candidatureService.getCandidaturesByIntervenant(intervenantId);

            // Check if no candidatures are found
            if (candidatures == null || candidatures.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"success\": false, \"message\": \"No candidatures found for the given intervenant.\"}")
                        .build();
            }

            // Transform candidatures into CandidatureRequestDTO
            // Inside the getAllCandidatures() method
            List<CandidatureRequestDTO> candidatureDTOs = candidatures.stream()
                    .map(candidature -> {
                        CandidatureRequestDTO dto = new CandidatureRequestDTO();
                        dto.setWorkRequestId(candidature.getWorkRequest().getId());
                        dto.setWorkTitle(candidature.getWorkRequest().getWorkTitle());  // Now set the work title properly
                        dto.setStatus(candidature.getStatus());
                        dto.setMessage(candidature.getMessage());
                        dto.setSubmissionDate(candidature.getSubmissionDate().toString());
                        dto.setCandidatureId(candidature.id);  // Set the candidature ID
                        return dto;
                    })
                    .collect(Collectors.toList());


            return Response.status(Response.Status.OK).entity(candidatureDTOs).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}")
                    .build();
        }
    }





    @DELETE
    @Path("/{candidatureId}")
    @Transactional
    public Response deleteCandidature(@PathParam("candidatureId") Long candidatureId) {
        try {
            // Find the candidature by its ID
            Candidature candidature = Candidature.findById(candidatureId);

            if (candidature == null) {
                // If no candidature is found, return a 404 NOT FOUND response
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"success\": false, \"message\": \"Candidature with ID " + candidatureId + " not found\"}")
                        .build();
            }

            // Delete the candidature
            candidature.delete();

            return Response.status(Response.Status.OK)
                    .entity("{\"success\": true, \"message\": \"Candidature deleted successfully\"}")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}")
                    .build();
        }
    }





}



