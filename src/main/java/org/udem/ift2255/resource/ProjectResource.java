package org.udem.ift2255.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.udem.ift2255.model.Project;
import org.udem.ift2255.model.Intervenant;
import org.udem.ift2255.service.NotificationService;
import org.udem.ift2255.service.ProjectService;

import java.time.LocalDate;
import java.util.List;

@Path("/projects")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProjectResource {

    @Inject
    ProjectService projectService;

    @Inject
    NotificationService notificationService;

    @POST
    @Path("/submit/{intervenantId}")
    @Transactional
    public Response submitProject(@PathParam("intervenantId") Long intervenantId, Project project) {
        try {
            // Find the intervenant submitting the project
            Intervenant intervenant = Intervenant.findById(intervenantId);
            if (intervenant == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"success\": false, \"message\": \"Intervenant not found.\"}")
                        .build();
            }

            // Set project owner to the found intervenant
            project.setProjectOwner(intervenant);
            project.setProjectStatus("Prévu");  // Set the status to "Prévu"

            // Ensure the neighbourhood is set
            if (project.getNeighbourhood() == null || project.getNeighbourhood().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"success\": false, \"message\": \"Neighbourhood cannot be null.\"}")
                        .build();
            }

            // Check for conflicts with residents' preferences based on the project dates
            if (projectService.checkForConflicts(project)) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"success\": false, \"message\": \"Conflict with resident's preferences.\"}")
                        .build();
            }

            // Save the project to the database
            project.persist();

            // Send notifications to residents in the same neighborhood
            notificationService.sendProjectNotification(project);

            return Response.status(Response.Status.CREATED)
                    .entity("{\"success\": true, \"message\": \"Project submitted successfully.\"}")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    // Endpoint to get all projects for a specific intervenant
    @GET
    @Path("/intervenant/{intervenantId}")
    public Response getProjectsForIntervenant(@PathParam("intervenantId") Long intervenantId) {
        try {
            // Fetch the projects for the intervenant
            List<Project> projects = projectService.getProjectsForIntervenant(intervenantId);

            // Check if no projects are found
            if (projects == null || projects.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"success\": false, \"message\": \"No projects found for the given intervenant.\"}")
                        .build();
            }

            return Response.status(Response.Status.OK).entity(projects).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    // Endpoint to delete a project
    @DELETE
    @Path("/{projectId}")
    @Transactional
    public Response deleteProject(@PathParam("projectId") Long projectId) {
        try {
            Project project = Project.findById(projectId);
            if (project == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"success\": false, \"message\": \"Project not found.\"}")
                        .build();
            }

            project.delete(); // Delete the project

            return Response.status(Response.Status.OK)
                    .entity("{\"success\": true, \"message\": \"Project deleted successfully.\"}")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}")
                    .build();
        }
    }
}
