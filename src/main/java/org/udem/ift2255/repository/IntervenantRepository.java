/**
 * Classe IntervenantRepository
 *
 * Cette classe est un dépôt (repository) pour gérer les entités Intervenant.
 * Elle utilise PanacheRepository pour simplifier les opérations CRUD sur les entités.
 * Cette classe est annotée avec @ApplicationScoped, ce qui signifie qu'une seule instance
 * est créée et partagée dans tout le cycle de vie de l'application.
 */
package org.udem.ift2255.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository; // Fournit des fonctionnalités simplifiées pour les dépôts
import org.udem.ift2255.model.Intervenant;                // Import de l'entité Intervenant
import jakarta.enterprise.context.ApplicationScoped;      // Spécifie le scope de l'application

/**
 * Dépôt IntervenantRepository
 *
 * Ce dépôt permet d'effectuer des opérations sur la table des intervenants,
 * telles que la recherche par email ou par ID.
 */
@ApplicationScoped
public class IntervenantRepository implements PanacheRepository<Intervenant> {

    /**
     * Recherche un intervenant par son email.
     *
     * @param email L'email de l'intervenant à rechercher.
     * @return L'entité Intervenant correspondante, ou {@code null} si elle n'existe pas.
     */
    public Intervenant findByEmail(String email) {
        return find("email", email).firstResult();
    }

    /**
     * Recherche un intervenant par son ID.
     *
     * @param id L'ID de l'intervenant à rechercher.
     * @return L'entité Intervenant correspondante, ou {@code null} si elle n'existe pas.
     */
    public Intervenant findById(Long id) {
        return findByIdOptional(id).orElse(null); // Gère les Optional avec un fallback à null
    }
}
