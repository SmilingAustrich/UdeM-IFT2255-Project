package org.udem.ift2255.repository;

import org.udem.ift2255.model.Resident;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ResidentRepository implements PanacheRepository<Resident> {

    // Find a Resident by ID (provided by Panache)
    public Resident findById(Long id) {
        return find("id", id).firstResult();
    }

    // Save a Resident (Panache handles persist and merge automatically)
    @Transactional
    public void save(Resident resident) {
        if (resident.isPersistent()) {
            resident.persist(); // Panache will handle both persist and merge
        } else {
            persist(resident); // Explicitly persist if it's a new entity
        }
    }

    // Delete a Resident
    @Transactional
    public void delete(Resident resident) {
        resident.delete(); // Panache handles removal
    }

    // Find a Resident by Email
    public Resident findByEmail(String email) {
        return find("email", email).firstResult();
    }
}
