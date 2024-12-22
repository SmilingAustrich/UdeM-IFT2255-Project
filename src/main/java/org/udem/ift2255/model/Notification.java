/**
 * Classe Notification
 *
 * Cette classe représente une notification dans l'application Ma Ville.
 * Une notification est associée à un résident et contient un message, une date de création,
 * ainsi qu'un indicateur pour savoir si elle a été lue ou non.
 * Elle est mappée à une table de base de données nommée "notification".
 */
package org.udem.ift2255.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity; // Fournit des fonctionnalités simplifiées pour les entités JPA
import jakarta.persistence.*;                         // Gestion des annotations JPA pour les entités
import java.time.LocalDateTime;                       // Représentation des dates et heures sans fuseau horaire

/**
 * Notification
 *
 * Cette classe encapsule les informations nécessaires à une notification,
 * comme le résident associé, le message, la date de création et le statut de lecture.
 */
@Entity
@Table(name = "notification") // Définit le nom de la table en base de données
public class Notification extends PanacheEntity {

    // Relation Many-to-One avec la classe Resident
    @ManyToOne
    @JoinColumn(name = "resident_id", nullable = false)
    private Resident resident;

    // Message contenu dans la notification
    private String message;

    // Date et heure de création de la notification
    private LocalDateTime dateCreated;

    // Statut indiquant si la notification a été lue ou non
    private boolean isRead;

    /**
     * Constructeur avec paramètres pour la classe Notification.
     * Initialise automatiquement la date de création et le statut de lecture par défaut.
     *
     * @param resident Le résident associé à la notification.
     * @param message  Le message contenu dans la notification.
     */
    public Notification(Resident resident, String message) {
        this.resident = resident;
        this.message = message;
        this.dateCreated = LocalDateTime.now(); // Date de création actuelle
        this.isRead = false; // Par défaut, une nouvelle notification n'est pas lue
    }

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Notification() {

    }

    /**
     * Retourne le résident associé à la notification.
     *
     * @return Le résident lié à cette notification.
     */
    public Resident getResident() {
        return resident;
    }

    /**
     * Définit le résident associé à la notification.
     *
     * @param resident Le résident à associer.
     */
    public void setResident(Resident resident) {
        this.resident = resident;
    }

    /**
     * Retourne le message contenu dans la notification.
     *
     * @return Le message sous forme de chaîne.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Définit le message contenu dans la notification.
     *
     * @param message Le message à définir.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Retourne la date de création de la notification.
     *
     * @return La date et l'heure de création sous forme de LocalDateTime.
     */
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * Définit la date de création de la notification.
     *
     * @param dateCreated La date et l'heure à définir.
     */
    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Retourne le statut de lecture de la notification.
     *
     * @return {@code true} si la notification a été lue, sinon {@code false}.
     */
    public boolean isRead() {
        return isRead;
    }

    /**
     * Définit le statut de lecture de la notification.
     *
     * @param read Le statut de lecture à définir.
     */
    public void setRead(boolean read) {
        isRead = read;
    }
}
