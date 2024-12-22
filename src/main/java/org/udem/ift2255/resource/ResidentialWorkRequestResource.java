package org.udem.ift2255.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.udem.ift2255.dto.CandidatureRequestDTO;
import org.udem.ift2255.dto.ResidentialWorkRequestDTO;
import org.udem.ift2255.dto.WorkRequestDTO;
import org.udem.ift2255.model.Candidature;
import org.udem.ift2255.model.Intervenant;
import org.udem.ift2255.service.CandidatureService;
import org.udem.ift2255.service.NotificationService;
import org.udem.ift2255.service.ResidentialWorkRequestService;
import org.udem.ift2255.model.ResidentialWorkRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Path("/work-requests")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ResidentialWorkRequestResource {

    @Inject
    ResidentialWorkRequestService workRequestService;

    @POST
    @Path("/create/{residentId}")
    public Response createWorkRequest(@PathParam("residentId") Long residentId, ResidentialWorkRequestDTO requestDTO) {
        try {
            workRequestService.saveRequest(requestDTO, residentId);
            return Response.status(Response.Status.CREATED).entity("Work request created successfully").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/resident/{residentId}")
    public Response getWorkRequestsByResident(@PathParam("residentId") Long residentId) {
        try {
            // Fetch the work requests using the service
            List<ResidentialWorkRequest> workRequests = workRequestService.getWorkRequestsByResident(residentId);

            if (workRequests == null || workRequests.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No work requests found for resident ID " + residentId).build();
            }

            // Map ResidentialWorkRequest to ResidentialWorkRequestDTO using setters
            List<ResidentialWorkRequestDTO> workRequestDTOs = workRequests.stream()
                    .map(workRequest -> {
                        ResidentialWorkRequestDTO dto = new ResidentialWorkRequestDTO();
                        dto.setId(workRequest.id);
                        dto.setWorkTitle(workRequest.getWorkTitle());
                        dto.setDetailedWorkDescription(workRequest.getDetailedWorkDescription());
                        dto.setNeighbourhood(workRequest.getNeighbourhood());
                        dto.setWorkType(workRequest.getWorkType());
                        dto.setWorkWishedStartDate(LocalDate.parse(workRequest.getWorkWishedStartDate().toString()));
                        return dto;
                    })
                    .collect(Collectors.toList());

            return Response.status(Response.Status.OK).entity(workRequestDTOs).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }




    @GET
    @Path("/all")
    public Response getAllWorkRequests() {
        try {
            // Get all work requests
            List<ResidentialWorkRequest> workRequests = workRequestService.getAllRequests();

            // Check if no work requests found
            if (workRequests == null || workRequests.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No work requests found")
                        .build();
            }

            // Convert ResidentialWorkRequest objects to WorkRequestDTO objects
            List<WorkRequestDTO> workRequestDTOs = workRequests.stream()
                    .map(request -> {
                        WorkRequestDTO dto = new WorkRequestDTO();
                        dto.setId(request.id);  // Set the ID field
                        dto.setWorkTitle(request.getWorkTitle());
                        dto.setDetailedWorkDescription(request.getDetailedWorkDescription());
                        dto.setQuartier(request.getNeighbourhood());
                        dto.setWorkType(request.getWorkType());
                        dto.setWorkWishedStartDate(request.getWorkWishedStartDate().toString());  // Convert LocalDate to String
                        return dto;
                    })
                    .collect(Collectors.toList());

            // Return the DTOs
            return Response.status(Response.Status.OK).entity(workRequestDTOs).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage())
                    .build();
        }
    }

    // In ResidentialWorkRequestResource.java
    public void setWorkRequestService(ResidentialWorkRequestService service) {
        this.workRequestService = service;
    }

}
