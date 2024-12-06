package org.udem.ift2255.repository;

import org.udem.ift2255.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

public abstract class Database {

    @PersistenceContext
    private static EntityManager entityManager;

    /**
     * Récupère un intervenant par son adresse email depuis la base de données.
     *
     * @param email L'adresse email de l'intervenant
     * @return L'intervenant correspondant à cet email, ou {@code null} si aucun intervenant n'existe avec cet email
     */
    public static Intervenant getIntervenantByEmail(String email) {
        try {
            return entityManager.createQuery("SELECT i FROM Intervenant i WHERE i.email = :email", Intervenant.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Récupère un résident par son adresse email depuis la base de données.
     *
     * @param email L'adresse email du résident
     * @return Le résident correspondant à cet email, ou {@code null} si aucun résident n'existe avec cet email
     */
    public static Resident getResidentByEmail(String email) {
        try {
            return entityManager.createQuery("SELECT r FROM Resident r WHERE r.email = :email", Resident.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Enregistrer un résident dans la base de données.
     *
     * @param resident L'objet résident à enregistrer.
     */
    @Transactional
    public static void saveResident(Resident resident) {
        entityManager.persist(resident);
    }

    /**
     * Enregistrer un intervenant dans la base de données.
     *
     * @param intervenant L'objet intervenant à enregistrer.
     */
    @Transactional
    public static void saveIntervenant(Intervenant intervenant) {
        entityManager.persist(intervenant);
    }

    /**
     * Enregistrer une requête de travail résidentielle dans la base de données.
     *
     * @param request La requête de travail à enregistrer.
     */
    @Transactional
    public static void saveResidentialWorkRequest(ResidentialWorkRequest request) {
        entityManager.persist(request);
    }

    /**
     * Récupère tous les résidents de la base de données.
     *
     * @return La liste de tous les résidents.
     */
    public static List<Resident> getAllResidents() {
        return entityManager.createQuery("SELECT r FROM Resident r", Resident.class).getResultList();
    }

    /**
     * Récupère tous les intervenants de la base de données.
     *
     * @return La liste de tous les intervenants.
     */
    public static List<Intervenant> getAllIntervenants() {
        return entityManager.createQuery("SELECT i FROM Intervenant i", Intervenant.class).getResultList();
    }

    /**
     * Récupère toutes les requêtes de travail résidentielles de la base de données.
     *
     * @return La liste de toutes les requêtes.
     */
    public static List<ResidentialWorkRequest> getAllResidentialWorkRequests() {
        return entityManager.createQuery("SELECT r FROM ResidentialWorkRequest r", ResidentialWorkRequest.class)
                .getResultList();
    }

    public static List<ResidentialWorkRequest> getAllRequests() {
        return entityManager.createQuery("SELECT r FROM ResidentialWorkRequest r", ResidentialWorkRequest.class)
                .getResultList();
    }

}
