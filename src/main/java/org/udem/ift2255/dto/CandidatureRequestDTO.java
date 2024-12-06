package org.udem.ift2255.dto;

public class CandidatureRequestDTO {

    private String titreRequete;  // Title of the request
    private String message;       // Candidature message

    // Getters and Setters

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
