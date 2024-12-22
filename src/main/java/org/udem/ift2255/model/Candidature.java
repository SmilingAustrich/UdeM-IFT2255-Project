package org.udem.ift2255.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "candidature")
public class Candidature extends PanacheEntity {

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "intervenant_id", nullable = false)
    private Intervenant intervenant;

    @ManyToOne
    @JoinColumn(name = "work_request_id", nullable = false)
    private ResidentialWorkRequest workRequest;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "submission_date", nullable = false)
    private LocalDate submissionDate;  // Add the submissionDate field (LocalDate)

    // Default constructor (required by Hibernate for proxy generation)
    public Candidature() {
    }

    // Constructor that accepts parameters
    public Candidature(Intervenant intervenant, String message, ResidentialWorkRequest workRequest) {
        this.intervenant = intervenant;
        this.message = message;
        this.workRequest = workRequest;
        this.status = "Soumise"; // Default status
        this.submissionDate = LocalDate.now(); // Automatically set the current date
    }

    // Getters and Setters
    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    public ResidentialWorkRequest getWorkRequest() {
        return workRequest;
    }

    public void setWorkRequest(ResidentialWorkRequest workRequest) {
        this.workRequest = workRequest;
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

    public LocalDate getSubmissionDate() {
        return this.submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    // Setters for IDs
    public void setIntervenantId(Long id) {
        this.intervenant = Intervenant.findById(id);  // Assign intervenant by ID
    }

    public Long getIntervenantId() {
        return this.intervenant.id;  // Return the intervenant ID
    }

    public void setWorkRequestId(Long workRequestId) {
        // Set workRequest by ID (but it's better to handle this with a workRequest object)
        this.workRequest = ResidentialWorkRequest.findById(workRequestId);
    }

    public Long getWorkRequestId() {
        return this.workRequest.id;  // Return the workRequest ID
    }
}
