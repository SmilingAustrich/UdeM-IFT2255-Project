package org.udem.ift2255.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "residential_work_request")
public class ResidentialWorkRequest extends PanacheEntity {



    @Column(name = "work_title", nullable = false)
    private String workTitle;

    @Column(name = "detailed_work_description", nullable = false)
    private String detailedWorkDescription;

    @Column(name = "work_type", nullable = false)
    private String workType;

    @Column(name = "neighbourhood", nullable = false)
    private String neighbourhood;

    @Column(name = "work_wished_start_date", nullable = false)
    private LocalDate workWishedStartDate;

    @ManyToOne
    @JoinColumn(name = "resident_id", nullable = false)
    private Resident resident;

    @Column(name = "is_work_available", nullable = false)
    private boolean isWorkAvailable;

    @OneToMany(mappedBy = "workRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidature> candidatures = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "chosen_intervenant_id")
    private Intervenant chosenIntervenant;

    // Default constructor
    public ResidentialWorkRequest() {}

    // Parameterized constructor
    public ResidentialWorkRequest(Resident resident, String workTitle, String detailedWorkDescription,
                                  String workType, LocalDate workWishedStartDate, String neighbourhood) {
        this.resident = resident;
        this.isWorkAvailable = true;
        this.workTitle = workTitle;
        this.detailedWorkDescription = detailedWorkDescription;
        this.workType = workType;
        this.workWishedStartDate = workWishedStartDate;
        this.neighbourhood = neighbourhood;
    }

    // Getters and Setters
    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
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

    public boolean isWorkAvailable() {
        return isWorkAvailable;
    }

    public void setWorkAvailable(boolean workAvailable) {
        isWorkAvailable = workAvailable;
    }

    public List<Candidature> getCandidatures() {
        return candidatures;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public Intervenant getChosenIntervenant() {
        return chosenIntervenant;
    }

    public void setChosenIntervenant(Intervenant chosenIntervenant) {
        this.chosenIntervenant = chosenIntervenant;
    }

    // Utility Methods
    public void ajouterCandidature(Intervenant intervenant, String message) {
        if (isWorkAvailable() && candidatures.stream().noneMatch(c -> c.getIntervenant().equals(intervenant))) {
            Candidature candidature = new Candidature();
            candidature.setIntervenant(intervenant);
            candidature.setWorkRequest(this);  // Link candidature to this request
            candidature.setMessage(message);
            candidatures.add(candidature);
            System.out.println("Candidature soumise par " + intervenant.getFirstName());
        } else {
            System.out.println("Candidature impossible ou déjà soumise par " + intervenant.getFirstName());
        }
    }

    public void retirerCandidature(Intervenant intervenant) {
        candidatures.removeIf(c -> c.getIntervenant().equals(intervenant));
        System.out.println("Candidature retirée par " + intervenant.getFirstName());
    }

    public void choisirCandidature(Intervenant intervenantChoisi) {
        candidatures.stream()
                .filter(c -> c.getIntervenant().equals(intervenantChoisi))
                .findFirst()
                .ifPresent(c -> {
                    this.chosenIntervenant = intervenantChoisi;
                    this.isWorkAvailable = false;  // Mark the work as unavailable once chosen
                    System.out.println("Candidature choisie : " + intervenantChoisi.getFirstName());
                });
    }

    @Override
    public String toString() {
        return "ResidentialWorkRequest{" +
                "id=" + id +
                ", workTitle='" + workTitle + '\'' +
                ", detailedWorkDescription='" + detailedWorkDescription + '\'' +
                ", workType='" + workType + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", workWishedStartDate=" + workWishedStartDate +
                ", resident=" + (resident != null ? resident.getFirstName() : "N/A") +
                ", isWorkAvailable=" + isWorkAvailable +
                ", candidatures=" + candidatures.size() +
                ", chosenIntervenant=" + (chosenIntervenant != null ? chosenIntervenant.getFirstName() : "N/A") +
                '}';
    }

    public String getTitle() {
        return this.workTitle;
    }

    public LocalDate getStartDate() {
        return workWishedStartDate;
    }
}
