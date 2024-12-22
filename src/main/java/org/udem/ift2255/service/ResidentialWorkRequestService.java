package org.udem.ift2255.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.udem.ift2255.dto.ResidentialWorkRequestDTO;
import org.udem.ift2255.model.ResidentialWorkRequest;
import org.udem.ift2255.model.Resident;
import org.udem.ift2255.repository.ResidentRepository;
import org.udem.ift2255.repository.ResidentialWorkRequestRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class ResidentialWorkRequestService {

    @Inject
    ResidentialWorkRequestRepository workRequestRepository;

    @Inject
    ResidentRepository residentRepository;

    @PersistenceContext
    EntityManager em;

    // Save a new work request
    @Transactional  // Ensure this method runs inside a transaction
    public void saveRequest(ResidentialWorkRequestDTO requestDTO, Long residentId) {
        // Fetch the Resident entity
        Resident resident = residentRepository.findById(residentId);
        if (resident == null) {
            throw new IllegalArgumentException("Resident not found for the provided ID");
        }

        // Validate input
        if (requestDTO.getWorkTitle() == null || requestDTO.getWorkTitle().isEmpty()) {
            throw new IllegalArgumentException("Work title is required");
        }

        if (requestDTO.getWorkWishedStartDate() == null || requestDTO.getWorkWishedStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date must be a valid future date");
        }

        // Create the work request and set its properties
        ResidentialWorkRequest request = new ResidentialWorkRequest();
        request.setAssignedResident(resident);
        request.setWorkTitle(requestDTO.getWorkTitle());
        request.setDetailedWorkDescription(requestDTO.getDetailedWorkDescription());
        request.setWorkType(requestDTO.getWorkType());
        request.setNeighbourhood(requestDTO.getNeighbourhood());
        request.setWorkWishedStartDate(requestDTO.getWorkWishedStartDate());

        // Persist the work request
        workRequestRepository.persist(request);
    }

    // Get all work requests
    public List<ResidentialWorkRequest> getAllRequests() {
        return workRequestRepository.listAll();  // Fetch all work requests
    }

    // Get a work request by its title
    public ResidentialWorkRequest getRequestByTitle(String title) {
        return workRequestRepository.find("workTitle", title).firstResult();  // Fetch by title
    }

    // Get filtered work requests based on work type and neighbourhood
    public List<ResidentialWorkRequest> getFilteredRequests(String workType, String neighbourhood) {
        return workRequestRepository.find("workType = ?1 and neighbourhood = ?2", workType, neighbourhood).list();  // Fetch filtered results
    }

    // Get a work request by its ID
    // Method to get a work request by ID
    public ResidentialWorkRequest getRequestById(Long workRequestId) {
        try {
            return em.find(ResidentialWorkRequest.class, workRequestId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // Fetch all work requests for a specific resident
    public List<ResidentialWorkRequest> getWorkRequestsByResident(Long residentId) {
        try {
            // Ensure the correct query to reference the 'assignedResident' relationship
            return em.createQuery("SELECT r FROM ResidentialWorkRequest r WHERE r.assignedResident.id = :residentId", ResidentialWorkRequest.class)
                    .setParameter("residentId", residentId)
                    .getResultList();
        } catch (Exception e) {
            // Log the error more effectively for debugging
            e.printStackTrace();
            return Collections.emptyList(); // Return an empty list instead of null
        }
    }


}
