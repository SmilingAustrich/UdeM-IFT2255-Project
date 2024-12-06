package org.udem.ift2255.dto;

import java.time.LocalDate;

public class WorkRequestDTO {
    private String workTitle;
    private String detailedWorkDescription;
    private String quartier;
    private int workType;
    private String workWishedStartDate;  // String type, parsed as LocalDate in the getter

    // Getters and Setters

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

    public int getWorkType() {
        return workType;
    }

    public void setWorkType(int workType) {
        this.workType = workType;
    }

    public LocalDate getWorkWishedStartDate() {
        return LocalDate.parse(workWishedStartDate);  // Parsing the String to LocalDate here
    }

    public void setWorkWishedStartDate(String workWishedStartDate) {
        this.workWishedStartDate = workWishedStartDate;
    }
}
