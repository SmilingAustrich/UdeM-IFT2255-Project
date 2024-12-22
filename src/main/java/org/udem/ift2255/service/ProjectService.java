package org.udem.ift2255.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.udem.ift2255.model.Project;

import java.util.List;

@ApplicationScoped
public class ProjectService {

    // Method to check for conflicts with residents' preferences
    public boolean checkForConflicts(Project project) {
        // Here you can implement the logic to check for conflicts.
        // For example, if there are existing projects or preferences from residents
        // that overlap with the start and end dates of the new project.

        // This is just a placeholder; actual conflict checking logic needs to be added.
        return false;
    }

    // Method to get all projects for a specific intervenant
    public List<Project> getProjectsForIntervenant(Long intervenantId) {
        // Query the database for projects where the intervenant ID matches
        return Project.find("projectOwner.id", intervenantId).list();  // Using Panache's find method to fetch projects
    }
}
