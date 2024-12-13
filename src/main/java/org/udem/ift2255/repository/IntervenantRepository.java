package org.udem.ift2255.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.udem.ift2255.model.Intervenant;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IntervenantRepository implements PanacheRepository<Intervenant> {

    // Find an Intervenant by Email
    public Intervenant findByEmail(String email) {
        return find("email", email).firstResult();
    }

    // Find an Intervenant by ID
    public Intervenant findById(Long id) {
        return findByIdOptional(id).orElse(null); // Optional handling with null fallback
    }
}
