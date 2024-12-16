package org.udem.ift2255.service;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.udem.ift2255.dto.CandidatureRequestDTO;
import org.udem.ift2255.model.Candidature;
import org.udem.ift2255.model.Intervenant;
import org.udem.ift2255.repository.Database;
import org.udem.ift2255.repository.IntervenantRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.udem.ift2255.model.ResidentialWorkRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class IntervenantService {

    @Inject
    IntervenantRepository intervenantRepository;


    public Intervenant getIntervenantById(Long intervenantId) {
        return intervenantRepository.findById(intervenantId);
    }

    public boolean authenticate(String email, String password) {
        Intervenant intervenant = intervenantRepository.findByEmail(email);
        if (intervenant != null && intervenant.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    /**
     * Permet à un intervenant de consulter la liste des requêtes de travail et de soumettre sa candidature.
     * Les requêtes peuvent être filtrées par type, quartier et par date de début.
     *
     * @param filterOption Option de filtrage choisie par l'intervenant.
     * @param filterValue Valeur de filtrage en fonction de l'option choisie.
     * @return Liste filtrée des requêtes de travail.
     */
    public List<ResidentialWorkRequest> consulterListeRequetesTravaux(int filterOption, String filterValue, List<ResidentialWorkRequest> allRequests) {
        List<ResidentialWorkRequest> filteredRequests = allRequests;

        switch (filterOption) {
            case 1: // Filtrer par type de travaux
                filteredRequests = filteredRequests.stream()
                        .filter(req -> req.getWorkType().equalsIgnoreCase(filterValue))
                        .collect(Collectors.toList());
                break;
            case 2: // Filtrer par quartier
                filteredRequests = filteredRequests.stream()
                        .filter(req -> req.getNeighbourhood().equalsIgnoreCase(filterValue))
                        .collect(Collectors.toList());
                break;
            case 3: // Filtrer par date de début
                try {
                    LocalDate dateDebut = LocalDate.parse(filterValue, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    filteredRequests = filteredRequests.stream()
                            .filter(req -> !req.getStartDate().isBefore(dateDebut))
                            .collect(Collectors.toList());
                } catch (Exception e) {
                    throw new IllegalArgumentException("Format de date invalide.", e);
                }
                break;
            case 4: // Pas de filtrage
                break;
            default:
                throw new IllegalArgumentException("Option de filtrage invalide.");
        }

        return filteredRequests;
    }

    /**
     * Permet à un intervenant de soumettre sa candidature à une requête de travail.
     *
     * @param titreRequete Le titre de la requête à laquelle l'intervenant souhaite soumettre sa candidature.
     * @param message Le message de candidature.
     * @param filteredRequests La liste filtrée des requêtes.
     * @return Retourne un message de succès ou d'échec.
     */
    public String soumettreCandidature(String titreRequete, String message, List<ResidentialWorkRequest> filteredRequests, Intervenant intervenant) {
        ResidentialWorkRequest requeteChoisie = filteredRequests.stream()
                .filter(req -> req.getTitle().equalsIgnoreCase(titreRequete))
                .findFirst()
                .orElse(null);

        if (requeteChoisie != null) {
            requeteChoisie.ajouterCandidature(intervenant, message);  // Pass the intervenant here
            return "Votre candidature a été soumise avec succès.";
        } else {
            throw new IllegalArgumentException("Requête non trouvée. Candidature non soumise.");
        }
    }




    /**
     * Permet à un intervenant de retirer sa candidature d'une requête de travail.
     *
     * @param intervenant L'intervenant qui retire sa candidature.
     * @param requete La requête de travail de laquelle retirer la candidature.
     */
    public void retirerCandidature(Intervenant intervenant, ResidentialWorkRequest requete) {
        List<Candidature> candidatures = requete.getCandidatures(); // Properly typed list

        if (candidatures.stream().anyMatch(c -> c.getIntervenant().equals(intervenant))) {
            requete.retirerCandidature(intervenant);
        } else {
            throw new IllegalStateException("Aucune candidature à retirer pour cet intervenant.");
        }
    }

    /**
     * Permet de choisir une candidature parmi celles soumises.
     *
     * @param intervenantChoisi L'intervenant choisi pour le projet.
     * @param requete La requête de travail à laquelle choisir une candidature.
     */
    public void choisirCandidature(Intervenant intervenantChoisi, ResidentialWorkRequest requete) {
        requete.choisirCandidature(intervenantChoisi);
    }

    @Inject
    IntervenantService intervenantService;

    @Inject
    ResidentialWorkRequestService workRequestService;  // Ensure this is injected

    @GET
    @Path("/consultRequetes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consulterRequetes(@QueryParam("filterOption") int filterOption,
                                      @QueryParam("filterValue") String filterValue) {
        List<ResidentialWorkRequest> allRequests = Database.getAllRequests(); // Replace with appropriate DB call
        List<ResidentialWorkRequest> filteredRequests = intervenantService.consulterListeRequetesTravaux(filterOption, filterValue, allRequests);

        return Response.ok(filteredRequests).build();
    }

    @POST
    @Path("/submitCandidature")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response soumettreCandidature(CandidatureRequestDTO candidatureRequestDTO, @QueryParam("intervenantId") Long intervenantId) {
        // Retrieve the Intervenant object based on the provided intervenantId
        Intervenant intervenant = intervenantService.getIntervenantById(intervenantId);  // Use the injected service

        if (intervenant == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Intervenant not found").build();
        }

        // Retrieve the work request associated with the given title (or other filtering criteria)
        ResidentialWorkRequest workRequest = workRequestService.getRequestByTitle(candidatureRequestDTO.getTitreRequete());  // Use the injected service

        if (workRequest == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Work request not found").build();
        }

        // Get the workType and neighbourhood from the workRequest object
        String workType = workRequest.getWorkType(); // Get work type from the request
        String neighbourhood = workRequest.getNeighbourhood(); // Get neighbourhood from the request

        // Retrieve filtered requests based on these parameters
        List<ResidentialWorkRequest> filteredRequests = workRequestService.getFilteredRequests(workType, neighbourhood);  // Use the injected service

        // Pass all necessary parameters to the service method
        String result = intervenantService.soumettreCandidature(candidatureRequestDTO.getTitreRequete(),
                candidatureRequestDTO.getMessage(),
                filteredRequests,
                intervenant);

        return Response.ok(result).build();
    }
}
