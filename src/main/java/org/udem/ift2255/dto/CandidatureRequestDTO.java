package org.udem.ift2255.dto;

import org.udem.ift2255.model.Candidature;

public class CandidatureRequestDTO {
    private Long id;  // The ID of the candidature
    private Long intervenantId; // ID of the Intervenant applying for the work request
    private String titreRequete;  // Title of the request
    private String message;       // Candidature message
    private String status;        // Status of the candidature (e.g., "Pending", "Accepted", etc.)
    private String submissionDate; // Date of submission (as a String)
    private Long workRequestId; // ID of the related work request

    // No-argument constructor (needed for instantiation without arguments)
    public CandidatureRequestDTO() {}

    // Constructor to map entity to DTO
    public CandidatureRequestDTO(Candidature candidature) {
        this.id = candidature.id;  // Candidature ID
        this.intervenantId = candidature.getIntervenant().id;
        this.message = candidature.getMessage();
        this.workRequestId = candidature.getWorkRequest().id;  // Mapping the related WorkRequest's ID
        this.status = candidature.getStatus();
        this.submissionDate = candidature.getSubmissionDate().toString(); // Convert to String if needed
        this.titreRequete = candidature.getWorkRequest().getWorkTitle();  // Set the work title from the related work request
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIntervenantId() {
        return intervenantId;
    }

    public void setIntervenantId(Long intervenantId) {
        this.intervenantId = intervenantId;
    }

    public String getTitreRequete() {
        return titreRequete;
    }

    public void setTitreRequete(String titreRequete) {
        this.titreRequete = titreRequete;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Long getWorkRequestId() {
        return workRequestId;
    }

    public void setWorkRequestId(Long workRequestId) {
        this.workRequestId = workRequestId;
    }

    public void setCandidatureId(Long id) {
        this.id = id;  // Set the candidature ID (this is the ID of the Candidature entity)
    }

    public void setWorkTitle(String workTitle) {
        this.titreRequete = workTitle;
    }
}
