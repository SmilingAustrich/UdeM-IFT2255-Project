package org.udem.ift2255.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.udem.ift2255.model.ResidentialWorkRequest;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ResidentialWorkRequestRepository implements PanacheRepository<ResidentialWorkRequest> {

    // Method to save a new request
    public void saveRequest(ResidentialWorkRequest requete) {
        persist(requete);  // PanacheRepository provides persist method
    }

    // Method to delete a request
    public void removeRequest(ResidentialWorkRequest requete) {
        delete(requete);  // PanacheRepository provides delete method
    }

    // Find a work request by its title
    public ResidentialWorkRequest findByTitle(String title) {
        // Use Panache's find method with JPQL to search by title
        return find("title", title).firstResult();
    }

    // Get a list of filtered requests based on workType and neighbourhood
    public List<ResidentialWorkRequest> findFilteredRequests(String workType, String neighbourhood) {
        // Use Panache's find method with JPQL to filter by workType and neighbourhood
        return find("workType = ?1 and neighbourhood = ?2", workType, neighbourhood).list();
    }

    // Get all work requests
    public List<ResidentialWorkRequest> getAllRequests() {
        // Use Panache's list() to get all requests
        return listAll();
    }
}
