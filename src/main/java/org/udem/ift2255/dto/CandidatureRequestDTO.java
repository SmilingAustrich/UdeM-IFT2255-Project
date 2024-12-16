package org.udem.ift2255.dto;

import org.udem.ift2255.model.Candidature;

public class CandidatureRequestDTO {

    private Long intervenantId; // ID of the Intervenant applying for the work request
    private String titreRequete;  // Title of the request
    private String message;       // Candidature message

    // No-argument constructor (needed for instantiation without arguments)
    public CandidatureRequestDTO() {}

    // Constructor to map entity to DTO
    public CandidatureRequestDTO(Candidature candidature) {
        this.intervenantId = candidature.getIntervenant().id;
        this.message = candidature.getMessage();
    }

    // Getters and Setters
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
}
