/**
 * Classe ResidentRepository
 *
 * Cette classe est un dépôt (repository) pour gérer les entités Resident.
 * Elle utilise PanacheRepository pour simplifier les opérations CRUD sur les résidents.
 * Cette classe est annotée avec @ApplicationScoped, ce qui signifie qu'une seule instance
 * est créée et partagée dans tout le cycle de vie de l'application.
 */
package org.udem.ift2255.repository;

import org.udem.ift2255.model.Resident;                 // Import de l'entité Resident
import io.quarkus.hibernate.orm.panache.PanacheRepository; // Fournit des fonctionnalités simplifiées pour les dépôts Panache
import jakarta.enterprise.context.ApplicationScoped;      // Spécifie le scope de l'application
import jakarta.transaction.Transactional;                // Gère les transactions

import java.util.List;                                     // Permet de manipuler des listes d'entités

/**
 * Dépôt ResidentRepository
 *
 * Ce dépôt permet d'effectuer des opérations spécifiques sur les résidents,
 * telles que la recherche par email, la sauvegarde, la suppression,
 * et la recherche par quartier.
 */
@ApplicationScoped
public class ResidentRepository implements PanacheRepository<Resident> {

    /**
     * Recherche un résident par son ID.
     *
     * @param id L'ID du résident à rechercher.
     * @return L'entité Resident correspondante, ou {@code null} si elle n'existe pas.
     */
    public Resident findById(Long id) {
        return find("id", id).firstResult(); // Recherche par ID
    }

    /**
     * Sauvegarde un résident.
     * Cette méthode gère à la fois l'enregistrement des nouveaux résidents
     * et la mise à jour des résidents existants.
     *
     * @param resident Le résident à sauvegarder.
     */
    @Transactional
    public void save(Resident resident) {
        if (resident.isPersistent()) {
            resident.persist(); // Panache gère automatiquement persist et merge
        } else {
            persist(resident); // Enregistre explicitement une nouvelle entité
        }
    }

    /**
     * Supprime un résident.
     *
     * @param resident Le résident à supprimer.
     */
    @Transactional
    public void delete(Resident resident) {
        resident.delete(); // Panache gère la suppression
    }

    /**
     * Recherche un résident par son email.
     *
     * @param email L'email du résident à rechercher.
     * @return L'entité Resident correspondante, ou {@code null} si elle n'existe pas.
     */
    public Resident findByEmail(String email) {
        return find("email", email).firstResult(); // Recherche par email
    }

    /**
     * Recherche les résidents par leur quartier.
     *
     * @param neighbourhood Le quartier des résidents à rechercher.
     * @return Une liste de résidents appartenant à ce quartier.
     */
    public List<Resident> findByNeighbourhood(String neighbourhood) {
        return find("neighbourhood", neighbourhood).list(); // Recherche par quartier
    }
}
