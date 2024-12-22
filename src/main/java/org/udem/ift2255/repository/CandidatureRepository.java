package org.udem.ift2255.repository;

import org.udem.ift2255.model.Candidature;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CandidatureRepository implements PanacheRepositoryBase<Candidature, Long> {

    // This class provides default methods for saving, updating, deleting, and querying Candidature entities
    // via PanacheRepositoryBase methods. You can add custom queries here if needed.
}
