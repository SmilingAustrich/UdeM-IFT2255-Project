package org.udem.ift2255.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "candidature")
public class Candidature extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "intervenant_id", nullable = false)
    private Intervenant intervenant;

    @ManyToOne
    @JoinColumn(name = "work_request_id", nullable = false)
    private ResidentialWorkRequest workRequest;

    @Column(name = "message", nullable = false)
    private String message;

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
}
