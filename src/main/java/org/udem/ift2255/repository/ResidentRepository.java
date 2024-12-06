package org.udem.ift2255.repository;

import org.udem.ift2255.model.Resident;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ResidentRepository implements PanacheRepository<Resident> {

    @Inject
    jakarta.persistence.EntityManager entityManager;

    // Example of using Panache methods
    public Resident findById(Long id) {
        return find("id", id).firstResult();  // Using Panache find()
    }

    // Custom save method using EntityManager
    @Transactional
    public void save(Resident resident) {
        if (resident.getId() == null) {
            entityManager.persist(resident);  // Perform persist if ID is null
        } else {
            entityManager.merge(resident);  // Perform merge (update) if ID exists
        }
    }

    @Transactional
    public void delete(Resident resident) {
        entityManager.remove(entityManager.contains(resident) ? resident : entityManager.merge(resident));
    }

    public Resident findByEmail(String email) {
        return find("email", email).firstResult();
    }
}
