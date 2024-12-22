package org.udem.ift2255.dto;

import java.time.LocalDate;

public class ResidentialWorkRequestDTO {

    private String workTitle;
    private String detailedWorkDescription;
    private String workType;
    private String neighbourhood;
    private LocalDate workWishedStartDate;

    // Getters and setters
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

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public LocalDate getWorkWishedStartDate() {
        return workWishedStartDate;
    }

    public void setWorkWishedStartDate(LocalDate workWishedStartDate) {
        this.workWishedStartDate = workWishedStartDate;
    }
}
