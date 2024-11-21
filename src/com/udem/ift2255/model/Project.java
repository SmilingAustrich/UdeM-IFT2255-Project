package com.udem.ift2255.model;

import java.io.Serializable;

public class Project implements Serializable {

    private final String projectName;
    private final String projectDescription;
    private final String projectType;
    private final String projectStatus;
    private final int StartDate;
    private final int EndDate;
    private final Intervenant projectOwner;
    private static final long serialVersionUID = 1L;
    public Project(String projectName, String projectDescription, String projectType, String projectStatus, int startDate, int endDate, Intervenant projectOwner) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectType = projectType;
        this.projectStatus = projectStatus;
        this.StartDate = startDate;
        this.EndDate = endDate;
        this.projectOwner = projectOwner;
    }
}

