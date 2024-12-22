package org.udem.ift2255.service;

import org.udem.ift2255.model.Notification;
import org.udem.ift2255.model.Project;
import org.udem.ift2255.model.ResidentialWorkRequest;
import org.udem.ift2255.model.Resident;
import org.udem.ift2255.repository.ResidentRepository;
import org.udem.ift2255.repository.NotificationRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class NotificationService {

    @Inject
    NotificationRepository notificationRepository;

    @Inject
    ResidentRepository residentRepository;  // Assuming you have a ResidentRepository to fetch residents by neighborhood

    // Send a notification to residents in the same neighbourhood as the project
    public void sendProjectNotification(Project project) {
        // Get all residents in the same neighbourhood
        List<Resident> residentsInSameNeighborhood = residentRepository.findByNeighbourhood(project.getNeighbourhood());

        for (Resident resident : residentsInSameNeighborhood) {
            // Create a new notification for each resident
            Notification notification = new Notification();
            notification.setResident(resident);
            notification.setMessage("New project in your neighbourhood: " + project.getProjectName());
            notification.setRead(false);

            // Persist the notification
            notificationRepository.persist(notification);
        }
    }

    // Method to create the notification
    public void createNotification(Notification notification) {
        try {
            notificationRepository.persist(notification); // Persist the notification
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create notification");
        }
    }

    // Fetch unread notifications for a resident
    public List<Notification> getUnreadNotifications(Long residentId) {
        return notificationRepository.findUnreadByResident(residentId);
    }

    // Mark a notification as read
    public void markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId);
        if (notification != null) {
            notification.setRead(true);
            notificationRepository.persist(notification);
        }
    }
}
