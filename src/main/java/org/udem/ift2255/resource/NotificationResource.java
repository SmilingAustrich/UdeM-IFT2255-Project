package org.udem.ift2255.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.udem.ift2255.model.Notification;
import org.udem.ift2255.service.NotificationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationResource {

    @Inject
    NotificationService notificationService;

    // Fetch unread notifications for a resident
    @GET
    @Path("/unread/{residentId}")
    public Response getUnreadNotifications(@PathParam("residentId") Long residentId) {
        try {
            List<Notification> unreadNotifications = notificationService.getUnreadNotifications(residentId);
            if (unreadNotifications.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT)
                        .entity("No unread notifications found.")
                        .build();
            }
            return Response.ok(unreadNotifications).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage())
                    .build();
        }
    }

    // Mark a notification as read
    @PATCH
    @Path("/mark-read/{notificationId}")
    public Response markNotificationAsRead(@PathParam("notificationId") Long notificationId) {
        try {
            notificationService.markNotificationAsRead(notificationId);
            return Response.ok("Notification marked as read").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage())
                    .build();
        }
    }

    // Create a new notification
    @POST
    @Path("/create")
    public Response createNotification(Notification notification) {
        try {
            notificationService.createNotification(notification);
            return Response.status(Response.Status.CREATED)
                    .entity("Notification created successfully")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error creating notification: " + e.getMessage())
                    .build();
        }
    }
}
