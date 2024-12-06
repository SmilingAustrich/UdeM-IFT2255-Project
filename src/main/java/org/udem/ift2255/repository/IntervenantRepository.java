package org.udem.ift2255.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.udem.ift2255.model.Intervenant;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IntervenantRepository implements PanacheRepository<Intervenant> {

    public Intervenant findByEmail(String email) {
        return find("email", email).firstResult();
    }

    // Method to find an Intervenant by ID
    public Intervenant findById(Long id) {
        // Use Panache's find method with JPQL to find by id
        return find("id", id).firstResult();  // Returns the first result or null if not found
    }}
