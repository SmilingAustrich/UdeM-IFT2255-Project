/**
 * Classe ResidentialWorkRequestRepository
 *
 * Cette classe est un dépôt (repository) pour gérer les entités ResidentialWorkRequest.
 * Elle utilise PanacheRepository pour simplifier les opérations CRUD sur les requêtes
 * de travaux résidentiels. Cette classe est annotée avec @ApplicationScoped,
 * ce qui signifie qu'une seule instance est créée et partagée dans tout le cycle
 * de vie de l'application.
 */
package org.udem.ift2255.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository; // Fournit des fonctionnalités simplifiées pour les dépôts Panache
import org.udem.ift2255.model.ResidentialWorkRequest;      // Import de l'entité ResidentialWorkRequest
import jakarta.enterprise.context.ApplicationScoped;       // Spécifie le scope de l'application

import java.util.List;                                     // Permet de manipuler des listes d'entités

/**
 * Dépôt ResidentialWorkRequestRepository
 *
 * Ce dépôt permet d'effectuer des opérations spécifiques sur les requêtes de travaux résidentiels,
 * comme l'enregistrement, la suppression, la recherche par titre ou par filtres.
 */
@ApplicationScoped
public class ResidentialWorkRequestRepository implements PanacheRepository<ResidentialWorkRequest> {

    /**
     * Enregistre une nouvelle requête de travaux résidentiels.
     *
     * @param requete La requête de travaux résidentiels à enregistrer.
     */
    public void saveRequest(ResidentialWorkRequest requete) {
        persist(requete); // Méthode fournie par PanacheRepository
    }

    /**
     * Supprime une requête de travaux résidentiels.
     *
     * @param requete La requête de travaux résidentiels à supprimer.
     */
    public void removeRequest(ResidentialWorkRequest requete) {
        delete(requete); // Méthode fournie par PanacheRepository
    }

    /**
     * Recherche une requête de travaux résidentiels par son titre.
     *
     * @param title Le titre de la requête.
     * @return La requête correspondante ou {@code null} si elle n'existe pas.
     */
    public ResidentialWorkRequest findByTitle(String title) {
        return find("title", title).firstResult(); // Recherche par titre
    }

    /**
     * Recherche les requêtes filtrées par type de travaux et quartier.
     *
     * @param workType     Le type de travaux.
     * @param neighbourhood Le quartier.
     * @return Une liste des requêtes correspondant aux filtres.
     */
    public List<ResidentialWorkRequest> findFilteredRequests(String workType, String neighbourhood) {
        return find("workType = ?1 and neighbourhood = ?2", workType, neighbourhood).list();
    }

    /**
     * Récupère toutes les requêtes de travaux résidentiels.
     *
     * @return Une liste contenant toutes les requêtes.
     */
    public List<ResidentialWorkRequest> getAllRequests() {
        return listAll(); // Récupère toutes les entités
    }

    /**
     * Recherche les requêtes de travaux associées à un résident spécifique.
     *
     * @param residentId L'ID du résident.
     * @return Une liste de requêtes associées à ce résident.
     */
    public List<ResidentialWorkRequest> findByResidentId(Long residentId) {
        return find("assignedResident.id", residentId).list();
    }
}
