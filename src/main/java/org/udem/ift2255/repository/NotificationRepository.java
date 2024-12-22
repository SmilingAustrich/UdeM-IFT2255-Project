package org.udem.ift2255.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.udem.ift2255.model.Notification;

import java.util.List;

@ApplicationScoped
public class NotificationRepository implements PanacheRepository<Notification> {

    // Custom queries using Panache's convenience methods
    public List<Notification> findUnreadByResident(Long residentId) {
        return find("resident.id = ?1 AND isRead = false", residentId).list();
    }

    public List<Notification> findByResident(Long residentId) {
        return find("resident.id = ?1", residentId).list();
    }
}
