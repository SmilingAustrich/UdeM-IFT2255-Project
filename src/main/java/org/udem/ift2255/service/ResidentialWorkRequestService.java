package org.udem.ift2255.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.udem.ift2255.model.ResidentialWorkRequest;
import org.udem.ift2255.repository.ResidentialWorkRequestRepository;

import java.util.List;

@ApplicationScoped
public class ResidentialWorkRequestService {

    @Inject
    private ResidentialWorkRequestRepository workRequestRepository;

    // Get a work request by its title
    public ResidentialWorkRequest getRequestByTitle(String title) {
        return workRequestRepository.findByTitle(title);  // Delegate to repository
    }

    // Get filtered work requests based on work type and neighbourhood
    public List<ResidentialWorkRequest> getFilteredRequests(String workType, String neighbourhood) {
        return workRequestRepository.findFilteredRequests(workType, neighbourhood);  // Delegate to repository
    }

    // Get all work requests
    public List<ResidentialWorkRequest> getAllRequests() {
        return workRequestRepository.findAll().list();  // Delegate to repository
    }
}
