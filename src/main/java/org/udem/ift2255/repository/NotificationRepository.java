/**
 * Classe NotificationRepository
 *
 * Cette classe est un dépôt (repository) pour gérer les entités Notification.
 * Elle utilise PanacheRepository pour simplifier les opérations CRUD sur les notifications.
 * Cette classe est annotée avec @ApplicationScoped, ce qui signifie qu'une seule instance
 * est créée et partagée dans tout le cycle de vie de l'application.
 */
package org.udem.ift2255.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository; // Fournit des fonctionnalités simplifiées pour les dépôts Panache
import jakarta.enterprise.context.ApplicationScoped;      // Spécifie le scope de l'application
import org.udem.ift2255.model.Notification;               // Import de l'entité Notification

import java.util.List;                                     // Permet de gérer des listes d'entités

/**
 * Dépôt NotificationRepository
 *
 * Ce dépôt permet d'effectuer des opérations spécifiques sur les notifications,
 * comme la recherche des notifications non lues ou la recherche par résident.
 */
@ApplicationScoped
public class NotificationRepository implements PanacheRepository<Notification> {

    /**
     * Recherche les notifications non lues pour un résident spécifique.
     *
     * @param residentId L'ID du résident.
     * @return Une liste de notifications non lues.
     */
    public List<Notification> findUnreadByResident(Long residentId) {
        return find("resident.id = ?1 AND isRead = false", residentId).list();
    }

    /**
     * Recherche toutes les notifications pour un résident spécifique.
     *
     * @param residentId L'ID du résident.
     * @return Une liste de toutes les notifications associées au résident.
     */
    public List<Notification> findByResident(Long residentId) {
        return find("resident.id = ?1", residentId).list();
    }
}
