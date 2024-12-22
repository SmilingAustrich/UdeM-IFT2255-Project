package org.udem.ift2255.dto;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class WorkRequestDTO {
    private Long id;  // ID should be included and handled
    private String workTitle;
    private String detailedWorkDescription;
    private String quartier;
    private String workType;
    private String workWishedStartDate;  // String type, parsed as LocalDate in the getter

    // Constructor
    public WorkRequestDTO(Long id, String workTitle, String detailedWorkDescription,
                          String quartier, String workType, String workWishedStartDate) {
        this.id = id;
        this.workTitle = workTitle;
        this.detailedWorkDescription = detailedWorkDescription;
        this.quartier = quartier;
        this.workType = workType;
        this.workWishedStartDate = workWishedStartDate;
    }

    public WorkRequestDTO() {}

    // Getters and Setters

    public Long getId() {
        return id;  // Return the ID as a Long
    }

    public void setId(Long id) {
        this.id = id;  // Set the ID
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    public String getDetailedWorkDescription() {
        return detailedWorkDescription;
    }

    public void setDetailedWorkDescription(String detailedWorkDescription) {
        this.detailedWorkDescription = detailedWorkDescription;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public LocalDate getWorkWishedStartDate() {
        // Error handling when parsing the date
        try {
            return LocalDate.parse(workWishedStartDate);  // Parsing the String to LocalDate
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + workWishedStartDate, e);
        }
    }

    public void setWorkWishedStartDate(String workWishedStartDate) {
        this.workWishedStartDate = workWishedStartDate;
    }
}
