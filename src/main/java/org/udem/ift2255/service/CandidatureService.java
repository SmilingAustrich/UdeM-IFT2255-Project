package org.udem.ift2255.service;

import org.udem.ift2255.model.Candidature;
import org.udem.ift2255.model.Intervenant;
import org.udem.ift2255.model.ResidentialWorkRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CandidatureService {

    // Fetch all candidatures for a specific intervenant
    public List<Candidature> getCandidaturesByIntervenant(Long intervenantId) {
        return Candidature.find("intervenant.id", intervenantId).list();
    }

    // Save a new candidature
    @Transactional
    public void saveCandidature(Candidature candidature) {
        Candidature.persist(candidature);
    }

    // Delete a candidature
    @Transactional
    public boolean deleteCandidature(Long candidatureId) {
        Candidature candidature = Candidature.findById(candidatureId);
        if (candidature != null) {
            candidature.delete();  // This will delete the candidature from the database
            return true;
        } else {
            return false;
        }
    }

}
