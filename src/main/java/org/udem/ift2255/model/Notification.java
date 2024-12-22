package org.udem.ift2255.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
public class Notification extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "resident_id", nullable = false)
    private Resident resident;

    private String message;
    private LocalDateTime dateCreated;
    private boolean isRead;

    public Notification(Resident resident, String message) {
        this.resident = resident;
        this.message = message;
        this.dateCreated = LocalDateTime.now();
        this.isRead = false;  // Default value for new notifications
    }

    public Notification() {

    }

    public Resident getResident() {
        return resident;
    }
    public void setResident(Resident resident) {
        this.resident = resident;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
    public boolean isRead() {
        return isRead;
    }
    public void setRead(boolean read) {
        isRead = read;
    }

}
