/**
 * Classe CandidatureRepository
 *
 * Cette classe est un dépôt (repository) pour gérer les entités Candidature.
 * Elle étend PanacheRepositoryBase, ce qui permet de simplifier les opérations
 * CRUD (Create, Read, Update, Delete) sur les entités Candidature.
 * Cette classe est annotée avec @ApplicationScoped, ce qui signifie
 * qu'elle est instanciée une seule fois pour toute la durée de l'application.
 */
package org.udem.ift2255.repository;

import org.udem.ift2255.model.Candidature; // Import de l'entité Candidature
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase; // Fournit des fonctionnalités pour les dépôts Panache
import jakarta.enterprise.context.ApplicationScoped;          // Spécifie le scope de l'application

/**
 * Dépôt CandidatureRepository
 *
 * Ce dépôt permet d'effectuer des opérations sur la table des candidatures
 * en utilisant l'entité Candidature comme type principal.
 * Il utilise Long comme type pour l'identifiant des entités.
 */
@ApplicationScoped
public class CandidatureRepository implements PanacheRepositoryBase<Candidature, Long> {

    // En étendant PanacheRepositoryBase, cette classe hérite de nombreuses méthodes utiles telles que :
    // - persist() : pour sauvegarder une entité
    // - findById() : pour récupérer une entité par son identifiant
    // - listAll() : pour lister toutes les entités
    // - delete() : pour supprimer une entité
}
