/**
 * Classe CandidatureResource
 *
 * Cette classe représente une ressource REST pour gérer les candidatures
 * soumises par des intervenants pour des requêtes de travaux résidentiels.
 * Elle permet de soumettre une candidature, de récupérer les candidatures associées
 * à un intervenant, et de supprimer une candidature spécifique.
 */
package org.udem.ift2255.resource;

import jakarta.inject.Inject;                          // Permet d'injecter des dépendances
import jakarta.transaction.Transactional;              // Gère les transactions
import jakarta.ws.rs.*;                                // Fournit les annotations pour les services REST
import jakarta.ws.rs.core.MediaType;                   // Spécifie les types de contenu pour les requêtes et réponses
import jakarta.ws.rs.core.Response;                    // Fournit des outils pour construire les réponses HTTP
import org.udem.ift2255.dto.CandidatureRequestDTO;     // DTO pour les candidatures
import org.udem.ift2255.model.*;                       // Import des entités Candidature, Intervenant et ResidentialWorkRequest
import org.udem.ift2255.service.CandidatureService;    // Service pour gérer les candidatures

import java.util.List;                                 // Permet de manipuler des listes
import java.util.stream.Collectors;                    // Fournit des outils pour transformer des flux

/**
 * Ressource REST pour la gestion des candidatures
 */
@Path("/candidatures")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CandidatureResource {

    @Inject
    CandidatureService candidatureService; // Service de gestion des candidatures

    /**
     * Soumet une candidature pour un intervenant donné.
     *
     * @param intervenantId       L'ID de l'intervenant.
     * @param candidatureRequest  Les détails de la candidature.
     * @return Une réponse HTTP indiquant le succès ou un message d'erreur.
     */
    @POST
    @Path("/{intervenantId}")
    public Response submitCandidature(@PathParam("intervenantId") Long intervenantId, CandidatureRequestDTO candidatureRequest) {
        try {
            Intervenant intervenant = Intervenant.findById(intervenantId);
            if (intervenant == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"success\": false, \"message\": \"Intervenant not found.\"}")
                        .build();
            }

            ResidentialWorkRequest workRequest = ResidentialWorkRequest.findById(candidatureRequest.getWorkRequestId());
            if (workRequest == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"success\": false, \"message\": \"Work request not found.\"}")
                        .build();
            }

            Candidature candidature = new Candidature(intervenant, candidatureRequest.getMessage(), workRequest);
            candidatureService.saveCandidature(candidature);

            return Response.status(Response.Status.OK)
                    .entity("{\"success\": true}")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"success\": false, \"message\": \"Une erreur interne est survenue\"}")
                    .build();
        }
    }

    /**
     * Récupère toutes les candidatures pour un intervenant donné.
     *
     * @param intervenantId L'ID de l'intervenant.
     * @return Une réponse contenant une liste de candidatures ou un message d'erreur.
     */
    @GET
    @Path("/{intervenantId}")
    public Response getAllCandidatures(@PathParam("intervenantId") Long intervenantId) {
        try {
            List<Candidature> candidatures = candidatureService.getCandidaturesByIntervenant(intervenantId);

            if (candidatures == null || candidatures.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"success\": false, \"message\": \"No candidatures found for the given intervenant.\"}")
                        .build();
            }

            List<CandidatureRequestDTO> candidatureDTOs = candidatures.stream()
                    .map(candidature -> {
                        CandidatureRequestDTO dto = new CandidatureRequestDTO();
                        dto.setWorkRequestId(candidature.getWorkRequest().getId());
                        dto.setWorkTitle(candidature.getWorkRequest().getWorkTitle());
                        dto.setStatus(candidature.getStatus());
                        dto.setMessage(candidature.getMessage());
                        dto.setSubmissionDate(candidature.getSubmissionDate().toString());
                        dto.setCandidatureId(candidature.id);
                        return dto;
                    })
                    .collect(Collectors.toList());

            return Response.status(Response.Status.OK).entity(candidatureDTOs).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    /**
     * Supprime une candidature donnée.
     *
     * @param candidatureId L'ID de la candidature à supprimer.
     * @return Une réponse HTTP indiquant le succès ou un message d'erreur.
     */
    @DELETE
    @Path("/{candidatureId}")
    @Transactional
    public Response deleteCandidature(@PathParam("candidatureId") Long candidatureId) {
        try {
            Candidature candidature = Candidature.findById(candidatureId);

            if (candidature == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"success\": false, \"message\": \"Candidature with ID " + candidatureId + " not found\"}")
                        .build();
            }

            candidature.delete();

            return Response.status(Response.Status.OK)
                    .entity("{\"success\": true, \"message\": \"Candidature deleted successfully\"}")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}")
                    .build();
        }
    }
}
