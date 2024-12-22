/**
 * Classe Candidature
 *
 * Cette classe représente une candidature soumise par un intervenant pour une requête
 * de travail résidentiel. Elle est mappée à une table de base de données nommée "candidature"
 * grâce à l'annotation @Entity. Elle utilise PanacheEntity pour simplifier les opérations
 * CRUD avec Quarkus.
 */
package org.udem.ift2255.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity; // Fournit des méthodes utilitaires pour l'accès aux données
import jakarta.persistence.*;                         // Pour la gestion des entités et des annotations JPA

import java.time.LocalDate;                           // Représentation des dates sans fuseau horaire

/**
 * Candidature
 *
 * Cette classe encapsule les informations liées à une candidature, telles que :
 * - Le statut de la candidature
 * - L'intervenant qui a soumis la candidature
 * - La requête de travail associée
 * - Le message accompagnant la candidature
 * - La date de soumission
 */
@Entity
@Table(name = "candidature") // Spécifie le nom de la table en base de données
public class Candidature extends PanacheEntity {

    // Statut de la candidature (exemple : "Soumise", "Acceptée")
    @Column(name = "status", nullable = false)
    private String status;

    // Intervenant ayant soumis la candidature (relation ManyToOne)
    @ManyToOne
    @JoinColumn(name = "intervenant_id", nullable = false)
    private Intervenant intervenant;

    // Requête de travail associée à la candidature (relation ManyToOne)
    @ManyToOne
    @JoinColumn(name = "work_request_id", nullable = false)
    private ResidentialWorkRequest workRequest;

    // Message accompagnant la candidature
    @Column(name = "message", nullable = false)
    private String message;

    // Date de soumission de la candidature
    @Column(name = "submission_date", nullable = false)
    private LocalDate submissionDate;

    /**
     * Constructeur par défaut requis par Hibernate pour la génération des proxys.
     */
    public Candidature() {
    }

    /**
     * Constructeur de la classe Candidature avec paramètres.
     * Initialise automatiquement le statut par défaut ("Soumise") et
     * la date de soumission à la date actuelle.
     *
     * @param intervenant  L'intervenant qui soumet la candidature.
     * @param message      Le message accompagnant la candidature.
     * @param workRequest  La requête de travail associée.
     */
    public Candidature(Intervenant intervenant, String message, ResidentialWorkRequest workRequest) {
        this.intervenant = intervenant;
        this.message = message;
        this.workRequest = workRequest;
        this.status = "Soumise"; // Statut par défaut
        this.submissionDate = LocalDate.now(); // Date actuelle
    }

    /**
     * Retourne l'intervenant ayant soumis la candidature.
     *
     * @return L'intervenant associé.
     */
    public Intervenant getIntervenant() {
        return intervenant;
    }

    /**
     * Définit l'intervenant ayant soumis la candidature.
     *
     * @param intervenant L'intervenant à associer.
     */
    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    /**
     * Retourne la requête de travail associée à la candidature.
     *
     * @return La requête de travail associée.
     */
    public ResidentialWorkRequest getWorkRequest() {
        return workRequest;
    }

    /**
     * Définit la requête de travail associée à la candidature.
     *
     * @param workRequest La requête de travail à associer.
     */
    public void setWorkRequest(ResidentialWorkRequest workRequest) {
        this.workRequest = workRequest;
    }

    /**
     * Retourne le message accompagnant la candidature.
     *
     * @return Le message de la candidature.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Définit le message accompagnant la candidature.
     *
     * @param message Le message à définir.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Retourne le statut actuel de la candidature.
     *
     * @return Le statut de la candidature.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Définit le statut actuel de la candidature.
     *
     * @param status Le statut à définir.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retourne la date de soumission de la candidature.
     *
     * @return La date de soumission sous forme de LocalDate.
     */
    public LocalDate getSubmissionDate() {
        return this.submissionDate;
    }

    /**
     * Définit la date de soumission de la candidature.
     *
     * @param submissionDate La date de soumission à définir.
     */
    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    /**
     * Définit l'intervenant associé en fonction de son ID.
     *
     * @param id L'ID de l'intervenant à associer.
     */
    public void setIntervenantId(Long id) {
        this.intervenant = Intervenant.findById(id); // Assign intervenant by ID
    }

    /**
     * Retourne l'ID de l'intervenant associé.
     *
     * @return L'ID de l'intervenant.
     */
    public Long getIntervenantId() {
        return this.intervenant.id; // Return the intervenant ID
    }

    /**
     * Définit la requête de travail associée en fonction de son ID.
     *
     * @param workRequestId L'ID de la requête de travail à associer.
     */
    public void setWorkRequestId(Long workRequestId) {
        this.workRequest = ResidentialWorkRequest.findById(workRequestId); // Set workRequest by ID
    }

    /**
     * Retourne l'ID de la requête de travail associée.
     *
     * @return L'ID de la requête de travail.
     */
    public Long getWorkRequestId() {
        return this.workRequest.id; // Return the workRequest ID
    }
}
