package org.udem.ift2255.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "project")
public class Project extends PanacheEntity {
    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(name = "project_description", length = 500)
    private String projectDescription;

    @Column(name = "project_type", nullable = false)
    private String projectType;

    @Column(name = "project_status", nullable = false)
    private String projectStatus;

    @Column(name = "start_date")
    private LocalDate startDate;  // Changed to LocalDate

    @Column(name = "end_date")
    private LocalDate endDate;  // Changed to LocalDate

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_owner_id", nullable = false)
    private Intervenant projectOwner;

    // Constructor with parameters
    public Project(String projectName, String projectDescription, String projectType, LocalDate startDate, LocalDate endDate, Intervenant projectOwner) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectType = projectType;
        this.projectStatus = "prévu";
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectOwner = projectOwner;
    }
    // Default constructor
    public Project() {
    }

    // Getters and setters (if needed)
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    // Getters and setters
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Intervenant getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(Intervenant projectOwner) {
        this.projectOwner = projectOwner;
    }
}
