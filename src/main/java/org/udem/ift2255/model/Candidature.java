package org.udem.ift2255.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
public class Candidature extends PanacheEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intervenant_id")
    private Intervenant intervenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_request_id")
    private ResidentialWorkRequest workRequest;

    @Column(length = 500)
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
