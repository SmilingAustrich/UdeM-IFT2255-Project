package org.udem.ift2255.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.udem.ift2255.dto.CandidatureRequestDTO;
import org.udem.ift2255.dto.ResidentialWorkRequestDTO;
import org.udem.ift2255.model.Candidature;
import org.udem.ift2255.model.Intervenant;
import org.udem.ift2255.model.Resident;
import org.udem.ift2255.model.ResidentialWorkRequest;
import org.udem.ift2255.repository.IntervenantRepository;
import org.udem.ift2255.repository.ResidentRepository;
import org.udem.ift2255.repository.ResidentialWorkRequestRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ResidentialWorkRequestService {

    @Inject
    ResidentialWorkRequestRepository workRequestRepository;

    @Inject
    IntervenantRepository intervenantRepository;

    @Inject
    ResidentRepository residentRepository;

    // Save a new work request
    public void saveRequest(ResidentialWorkRequestDTO requestDTO, Long residentId) {
        ResidentialWorkRequest request = new ResidentialWorkRequest();
        Resident resident = residentRepository.findById(residentId); // Fetch the Resident entity
        if (resident == null) {
            throw new IllegalArgumentException("Resident not found for the provided ID");
        }if (requestDTO.getWorkTitle() == null || requestDTO.getWorkTitle().isEmpty()) {
            throw new IllegalArgumentException("Work title is required");
        }

        if (requestDTO.getWorkWishedStartDate() == null || requestDTO.getWorkWishedStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date must be a valid future date");
        }

        request.setResident(resident);
        request.setWorkTitle(requestDTO.getWorkTitle());
        request.setDetailedWorkDescription(requestDTO.getDetailedWorkDescription());
        request.setWorkType(requestDTO.getWorkType());
        request.setNeighbourhood(requestDTO.getNeighbourhood());
        request.setWorkWishedStartDate(requestDTO.getWorkWishedStartDate());
        workRequestRepository.persist(request); // Save the ResidentialWorkRequest
    }


    // Add a candidature to a work request
    public void addCandidature(Long workRequestId, Long intervenantId, String message) {
        // Fetch work request and intervenant using Panache
        ResidentialWorkRequest request = workRequestRepository.findById(workRequestId);
        Intervenant intervenant = intervenantRepository.findById(intervenantId);

        if (request == null) {
            throw new IllegalArgumentException("Work request not found.");
        }
        if (intervenant == null) {
            throw new IllegalArgumentException("Intervenant not found.");
        }

        // Check if the candidature already exists or if the work is available
        if (request.isWorkAvailable() &&
                request.getCandidatures().stream().noneMatch(c -> c.getIntervenant().equals(intervenant))) {
            // Create and add candidature
            Candidature candidature = new Candidature();
            candidature.setIntervenant(intervenant);
            candidature.setWorkRequest(request);
            candidature.setMessage(message);

            // Add candidature to the work request
            request.getCandidatures().add(candidature);
            System.out.println("Candidature soumise par " + intervenant.getFirstName());
        } else {
            throw new IllegalStateException("Candidature impossible ou déjà soumise.");
        }
    }

    // Remove a candidature from a work request
    public void removeCandidature(Long workRequestId, Long intervenantId) {
        ResidentialWorkRequest request = workRequestRepository.findById(workRequestId);
        Intervenant intervenant = intervenantRepository.findById(intervenantId);

        if (request == null || intervenant == null) {
            throw new IllegalArgumentException("Work request or intervenant not found.");
        }

        boolean removed = request.getCandidatures().removeIf(c -> c.getIntervenant().equals(intervenant));
        if (removed) {
            System.out.println("Candidature retirée par " + intervenant.getFirstName());
        } else {
            throw new IllegalStateException("Candidature introuvable pour cet intervenant.");
        }
    }

    // Choose a candidature for a work request
    public void chooseCandidature(Long workRequestId, Long intervenantId) {
        ResidentialWorkRequest request = workRequestRepository.findById(workRequestId);
        Intervenant intervenant = intervenantRepository.findById(intervenantId);

        if (request == null) {
            throw new IllegalArgumentException("Work request not found.");
        }
        if (intervenant == null) {
            throw new IllegalArgumentException("Intervenant not found.");
        }

        // Check if the candidature exists
        if (request.getCandidatures().stream().noneMatch(c -> c.getIntervenant().equals(intervenant))) {
            throw new IllegalStateException("Candidature not found for the specified intervenant.");
        }

        // Set the chosen intervenant
        request.setChosenIntervenant(intervenant);
        request.setWorkAvailable(false); // Close the work request
        System.out.println("Candidature choisie : " + intervenant.getFirstName());
    }

    // Get a work request by its title
    public ResidentialWorkRequest getRequestByTitle(String title) {
        return workRequestRepository.findByTitle(title);
    }

    // Get all work requests
    public List<ResidentialWorkRequest> getAllRequests() {
        return workRequestRepository.listAll();
    }

    // Get filtered work requests
    public List<ResidentialWorkRequest> getFilteredRequests(String workType, String neighbourhood) {
        return workRequestRepository.findFilteredRequests(workType, neighbourhood);
    }

    public List<ResidentialWorkRequestDTO> getResidentWorkRequests(Long residentId) {
        return workRequestRepository.find("resident.id", residentId).stream()
                .map(request -> {
                    ResidentialWorkRequestDTO dto = new ResidentialWorkRequestDTO();
                    dto.setId(request.id);
                    dto.setWorkTitle(request.getWorkTitle());
                    dto.setWorkType(request.getWorkType());
                    dto.setNeighbourhood(request.getNeighbourhood());
                    dto.setWorkWishedStartDate(request.getWorkWishedStartDate());
                    dto.setCandidatures(request.getCandidatures().stream()
                            .map(c -> {
                                CandidatureRequestDTO candidatureDTO = new CandidatureRequestDTO();
                                candidatureDTO.setIntervenantId(c.getIntervenant().id);
                                candidatureDTO.setTitreRequete(request.getWorkTitle()); // Title of the work request
                                candidatureDTO.setMessage(c.getMessage());
                                return candidatureDTO;
                            })
                            .collect(Collectors.toList()));

                    return dto;
                })
                .collect(Collectors.toList());
    }


    public void acceptCandidature(Long requestId, Long intervenantId) {
        ResidentialWorkRequest request = workRequestRepository.findById(requestId);
        if (request == null) {
            throw new IllegalArgumentException("Requête introuvable.");
        }

        Intervenant intervenant = intervenantRepository.findById(intervenantId);
        if (intervenant == null) {
            throw new IllegalArgumentException("Intervenant introuvable.");
        }

        request.setChosenIntervenant(intervenant);
        request.setWorkAvailable(false);
        workRequestRepository.persist(request);
    }

}
