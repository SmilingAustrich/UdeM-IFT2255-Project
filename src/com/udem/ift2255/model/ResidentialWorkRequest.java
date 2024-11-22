package com.udem.ift2255.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;


public class ResidentialWorkRequest implements Serializable {
    private final String workTitle;
    private final String detailedWorkDescription;
    private final String workType;
    private final String neighbourhood;
    private final LocalDate workWishedStartDate;
    private final Resident resident;



    private boolean isWorkAvailable;
    private final Map<Intervenant, String> candidatures;
    private Intervenant chosenIntervenant;
    private static final long serialVersionUID = 1L;

    public ResidentialWorkRequest(Resident resident, String workTitle, String detailedWorkDescription, String workType, LocalDate workWishedStartDate, String quartier) {
        this.resident = resident;
        this.isWorkAvailable = true;
        this.candidatures = new HashMap<>();
        this.workTitle = workTitle;
        this.detailedWorkDescription = detailedWorkDescription;
        this.workType = workType;
        this.workWishedStartDate = workWishedStartDate;
        this.neighbourhood = quartier;
    }

    public Resident getResident() {
        return resident;
    }

    public String getDescription() {
        return detailedWorkDescription;
    }

    public boolean isWorkAvailable() {
        return isWorkAvailable;
    }

    public void rendreDisponible() {
        this.isWorkAvailable = true;
    }

    public void rendreIndisponible() {
        this.isWorkAvailable = false;
    }

    public void ajouterCandidature(Intervenant intervenant, String message) {
        if (isWorkAvailable() && !candidatures.containsKey(intervenant)) {
            candidatures.put(intervenant, message);
            System.out.println("Candidature soumise par " + intervenant.getFirstName());
        } else if (candidatures.containsKey(intervenant)) {
            System.out.println("La candidature a déjà été soumise par " + intervenant.getFirstName());
        } else {
            System.out.println("Impossible de soumettre la candidature.");
        }
    }



    public void retirerCandidature(Intervenant intervenant){
        candidatures.remove(intervenant);
        System.out.println("Candidature retirée par " + intervenant.getFirstName());
    }

    //TODO: implementer et lié la méthode pour le devoir 3
    public void choisirCandidature(Intervenant intervenantChoisi, String messageResident){
        if (candidatures.containsKey(intervenantChoisi)){
            this.chosenIntervenant = intervenantChoisi;
            System.out.println("Candidature choisie : " + intervenantChoisi.getFirstName());
            if (messageResident != null){
                System.out.println("Message du résident : " + messageResident);
            }
        }
    }



    public String getTitle() {
        return workTitle;
    }

    public LocalDate getStartDate() {
        return workWishedStartDate;
    }

    public String getWorkType() {
        return workType;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public String getDetailedWorkDescription() {
        return detailedWorkDescription;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public LocalDate getWorkWishedStartDate() {
        return workWishedStartDate;
    }

    public String getQuartier() {
        return neighbourhood;
    }

    public Map<Intervenant, String> getCandidaturesMap() {
        return candidatures;
    }

public Map<Intervenant, String> getCandidatures(){
return candidatures;}
}
