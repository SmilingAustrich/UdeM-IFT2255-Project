/**
 * Classe CandidatureRequestDTO
 *
 * Cette classe sert de DTO (Data Transfer Object) pour la classe Candidature.
 * Elle contient les informations nécessaires pour transférer les données
 * entre la couche de présentation (par exemple un contrôleur REST) et la couche
 * métier ou persistance, sans exposer directement l'entité.
 */
package org.udem.ift2255.dto;

import org.udem.ift2255.model.Candidature;

/**
 * CandidatureRequestDTO
 *
 * Cette classe contient des champs représentant les informations essentielles
 * d'une candidature : identifiants, titre de la requête, message, statut, date de soumission,
 * et identifiants associés (candidature, intervenant, requête de travail).
 * Elle fournit également des méthodes accesseurs et mutateurs
 * (getters et setters) pour chacun de ces champs.
 */
public class CandidatureRequestDTO {

    // Identifiant unique de la candidature
    private Long id;  // The ID of the candidature

    // Identifiant de l'Intervenant associé
    private Long intervenantId; // ID of the Intervenant applying for the work request

    // Titre de la requête de travail
    private String titreRequete;  // Title of the request

    // Message lié à la candidature
    private String message;       // Candidature message

    // Statut de la candidature (ex: "Pending", "Accepted", etc.)
    private String status;        // Status of the candidature (e.g., "Pending", "Accepted", etc.)

    // Date de soumission de la candidature (en format String)
    private String submissionDate; // Date of submission (as a String)

    // Identifiant de la requête de travail associée
    private Long workRequestId; // ID of the related work request

    /**
     * Constructeur sans arguments requis pour la désérialisation.
     */
    public CandidatureRequestDTO() {}

    /**
     * Constructeur permettant de mapper une entité Candidature vers le DTO.
     *
     * @param candidature L'entité Candidature à convertir en DTO.
     */
    public CandidatureRequestDTO(Candidature candidature) {
        this.id = candidature.id;  // Candidature ID
        this.intervenantId = candidature.getIntervenant().id;
        this.message = candidature.getMessage();
        this.workRequestId = candidature.getWorkRequest().id;  // Mapping the related WorkRequest's ID
        this.status = candidature.getStatus();
        this.submissionDate = candidature.getSubmissionDate().toString(); // Convert to String if needed
        this.titreRequete = candidature.getWorkRequest().getWorkTitle();  // Set the work title from the related work request
    }

    /**
     * Retourne l'identifiant de la candidature.
     * @return L'ID de la candidature.
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant de la candidature.
     * @param id L'ID à assigner à la candidature.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne l'ID de l'Intervenant associé à la candidature.
     * @return L'ID de l'Intervenant.
     */
    public Long getIntervenantId() {
        return intervenantId;
    }

    /**
     * Définit l'ID de l'Intervenant associé à la candidature.
     * @param intervenantId L'ID de l'Intervenant.
     */
    public void setIntervenantId(Long intervenantId) {
        this.intervenantId = intervenantId;
    }

    /**
     * Retourne le titre de la requête liée à la candidature.
     * @return Le titre de la requête.
     */
    public String getTitreRequete() {
        return titreRequete;
    }

    /**
     * Définit le titre de la requête liée à la candidature.
     * @param titreRequete Le titre de la requête.
     */
    public void setTitreRequete(String titreRequete) {
        this.titreRequete = titreRequete;
    }

    /**
     * Retourne le message de la candidature.
     * @return Le message de la candidature.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Définit le message de la candidature.
     * @param message Le message à définir.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Retourne le statut de la candidature.
     * @return Le statut de la candidature.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Définit le statut de la candidature.
     * @param status Le statut (ex: "Pending", "Accepted", etc.).
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retourne la date de soumission de la candidature (sous forme de chaîne).
     * @return La date de soumission.
     */
    public String getSubmissionDate() {
        return submissionDate;
    }

    /**
     * Définit la date de soumission de la candidature (sous forme de chaîne).
     * @param submissionDate La date de soumission en String.
     */
    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    /**
     * Retourne l'identifiant de la requête de travail associée à la candidature.
     * @return L'ID de la requête de travail.
     */
    public Long getWorkRequestId() {
        return workRequestId;
    }

    /**
     * Définit l'identifiant de la requête de travail associée à la candidature.
     * @param workRequestId L'ID de la requête de travail.
     */
    public void setWorkRequestId(Long workRequestId) {
        this.workRequestId = workRequestId;
    }

    /**
     * Assigne l'ID de la candidature (entité Candidature).
     * @param id L'ID à assigner.
     */
    public void setCandidatureId(Long id) {
        this.id = id;  // Set the candidature ID (this is the ID of the Candidature entity)
    }

    /**
     * Assigne le titre de la requête de travail associée.
     * @param workTitle Le titre de la requête.
     */
    public void setWorkTitle(String workTitle) {
        this.titreRequete = workTitle;
    }
}
