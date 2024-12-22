package org.udem.ift2255.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "residential_work_request")
public class ResidentialWorkRequest extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "assigned_resident_id", referencedColumnName = "id")
    private Resident assignedResident;

    @Column(name = "work_title")
    private String workTitle;

    @Column(name = "detailed_work_description")
    private String detailedWorkDescription;

    @Column(name = "work_type")
    private String workType;

    @Column(name = "neighbourhood")
    private String neighbourhood;

    @Column(name = "work_wished_start_date")
    private LocalDate workWishedStartDate;

    @OneToMany(mappedBy = "workRequest")
    private List<Candidature> candidatures;

    @Column(name = "is_work_available")
    private boolean isWorkAvailable;

    @ManyToOne
    @JoinColumn(name = "chosen_intervenant_id", referencedColumnName = "id", nullable = true)
    private Intervenant chosenIntervenant;

    // Getters and setters
    public String getTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }


    public LocalDate getStartDate() {
        return workWishedStartDate;
    }

    public String getDetailedWorkDescription() {
        return detailedWorkDescription;
    }

    public void setDetailedWorkDescription(String detailedWorkDescription) {
        this.detailedWorkDescription = detailedWorkDescription;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public LocalDate getWorkWishedStartDate() {
        return workWishedStartDate;
    }

    public void setWorkWishedStartDate(LocalDate workWishedStartDate) {
        this.workWishedStartDate = workWishedStartDate;
    }

    public List<Candidature> getCandidatures() {
        return candidatures;
    }

    public void setCandidatures(List<Candidature> candidatures) {
        this.candidatures = candidatures;
    }

    public boolean isWorkAvailable() {
        return isWorkAvailable;
    }

    public void setWorkAvailable(boolean workAvailable) {
        isWorkAvailable = workAvailable;
    }

    public Intervenant getChosenIntervenant() {
        return chosenIntervenant;
    }

    public void setChosenIntervenant(Intervenant chosenIntervenant) {
        this.chosenIntervenant = chosenIntervenant;
    }

    public Resident getAssignedResident() {
        return assignedResident;
    }

    public void setAssignedResident(Resident assignedResident) {
        this.assignedResident = assignedResident;
    }

    // Default constructor
    public ResidentialWorkRequest() {
    }

    // Constructor with parameters
    public ResidentialWorkRequest(Resident assignedResident, String workTitle, String detailedWorkDescription, String workType, LocalDate workWishedStartDate, String neighbourhood) {
        this.assignedResident = assignedResident;
        this.workTitle = workTitle;
        this.detailedWorkDescription = detailedWorkDescription;
        this.workType = workType;
        this.workWishedStartDate = workWishedStartDate;
        this.neighbourhood = neighbourhood;
        this.isWorkAvailable = true; // Default to true
    }

    // Add missing methods for getters
    // Removed duplicate getter methods

    // ResidentialWorkRequest.java
    public void ajouterCandidature(Intervenant intervenant, String message) {
        Candidature candidature = new Candidature(intervenant, message, this); // Create a new candidature
        candidatures.add(candidature); // Add the candidature to the list
    }

    // ResidentialWorkRequest.java
    public void retirerCandidature(Intervenant intervenant) {
        candidatures.removeIf(c -> c.getIntervenant().equals(intervenant)); // Removes the candidature with the specified intervenant
    }

    // ResidentialWorkRequest.java
    public void choisirCandidature(Intervenant intervenant) {
        for (Candidature candidature : candidatures) {
            if (candidature.getIntervenant().equals(intervenant)) {
                this.chosenIntervenant = intervenant; // Sets the chosen intervenant for the work request
                break;
            }
        }
    }


    public String getWorkTitle() {
        return this.workTitle;
    }

    public Long getId() {
        return this.id;
    }
}

