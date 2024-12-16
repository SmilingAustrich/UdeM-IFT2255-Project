package org.udem.ift2255.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.udem.ift2255.model.Candidature;

public class ResidentialWorkRequestDTO {

    private Long id; // ID of the work request
    private String workTitle; // Title of the work request
    private String detailedWorkDescription; // Detailed description
    private String workType; // Type of work
    private String neighbourhood; // Neighbourhood of the request
    private LocalDate workWishedStartDate; // Desired start date
    private List<CandidatureRequestDTO> candidatures; // List of candidatures

    // Default constructor
    public ResidentialWorkRequestDTO() {
    }

    // Constructor to map entity to DTO
    public ResidentialWorkRequestDTO(org.udem.ift2255.model.ResidentialWorkRequest request) {
        this.id = request.id; // Panache automatically generates IDs
        this.workTitle = request.getWorkTitle();
        this.detailedWorkDescription = request.getDetailedWorkDescription();
        this.workType = request.getWorkType();
        this.neighbourhood = request.getNeighbourhood();
        this.workWishedStartDate = request.getWorkWishedStartDate();

        // Map candidatures to DTOs
        if (request.getCandidatures() != null) {
            this.candidatures = request.getCandidatures().stream()
                    .map(CandidatureRequestDTO::new)
                    .collect(Collectors.toList());
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<CandidatureRequestDTO> getCandidatures() {
        return candidatures;
    }

    public void setCandidatures(List<CandidatureRequestDTO> candidatures) {
        this.candidatures = candidatures;
    }
}
