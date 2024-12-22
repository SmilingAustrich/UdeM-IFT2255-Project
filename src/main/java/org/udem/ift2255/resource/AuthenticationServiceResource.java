/**
 * Classe AuthenticationServiceResource
 *
 * Cette classe représente une ressource REST pour gérer l'authentification
 * et l'inscription des utilisateurs (résidents et intervenants) dans l'application Ma Ville.
 * Elle fournit des points d'entrée pour les opérations d'authentification et de création
 * de nouveaux comptes.
 */
package org.udem.ift2255.resource;

import jakarta.json.Json;                              // Fournit des outils pour créer et manipuler des objets JSON
import org.udem.ift2255.service.AuthenticationService; // Service d'authentification
import org.udem.ift2255.dto.LoginRequestDTO;           // DTO pour les requêtes de connexion
import org.udem.ift2255.model.*;                       // Import des entités Resident et Intervenant
import org.udem.ift2255.repository.ResidentRepository; // Dépôt pour gérer les résidents
import org.udem.ift2255.repository.IntervenantRepository; // Dépôt pour gérer les intervenants

import jakarta.enterprise.context.ApplicationScoped;   // Spécifie le scope de l'application
import jakarta.inject.Inject;                          // Permet d'injecter des dépendances
import jakarta.transaction.Transactional;              // Gestion des transactions
import jakarta.ws.rs.*;                                // Fournit les annotations pour les services REST
import jakarta.ws.rs.core.MediaType;                   // Spécifie les types de contenu pour les requêtes et réponses
import jakarta.ws.rs.core.Response;                    // Fournit des outils pour construire les réponses HTTP

/**
 * Ressource REST pour l'authentification et l'inscription
 */
@ApplicationScoped
@Path("/authenticate")
public class AuthenticationServiceResource {

    @Inject
    ResidentRepository residentRepository; // Dépôt pour les résidents

    @Inject
    IntervenantRepository intervenantRepository; // Dépôt pour les intervenants

    @Inject
    AuthenticationService authenticationService; // Service d'authentification

    /**
     * Authentifie un résident avec l'email et le mot de passe fournis.
     *
     * @param loginRequest Les informations de connexion (email et mot de passe).
     * @return Une réponse contenant les détails du résident ou un message d'erreur.
     */
    @POST
    @Path("/resident")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateResident(LoginRequestDTO loginRequest) {
        Resident resident = residentRepository.find("email", loginRequest.getEmail()).firstResult();

        // Vérifie le mot de passe en clair (sans chiffrement pour simplifier)
        if (resident != null && loginRequest.getPassword().equals(resident.getPassword())) {
            return Response.ok()
                    .entity(Json.createObjectBuilder()
                            .add("residentId", resident.id)
                            .add("email", resident.getEmail())
                            .build())
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid credentials")
                    .build();
        }
    }

    /**
     * Authentifie un intervenant avec l'email et le mot de passe fournis.
     *
     * @param loginRequest Les informations de connexion (email et mot de passe).
     * @return Une réponse contenant les détails de l'intervenant ou un message d'erreur.
     */
    @POST
    @Path("/intervenant")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateIntervenant(LoginRequestDTO loginRequest) {
        Intervenant intervenant = intervenantRepository.find("email", loginRequest.getEmail()).firstResult();

        // Vérifie le mot de passe en clair (sans chiffrement pour simplifier)
        if (intervenant != null && loginRequest.getPassword().equals(intervenant.getPassword())) {
            return Response.ok()
                    .entity(Json.createObjectBuilder()
                            .add("message", "Intervenant authenticated successfully")
                            .add("intervenantId", intervenant.id)
                            .add("email", intervenant.getEmail())
                            .build())
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Json.createObjectBuilder()
                            .add("message", "Invalid credentials")
                            .build())
                    .build();
        }
    }

    /**
     * Inscrit un nouveau résident.
     *
     * @param resident L'objet Resident à enregistrer.
     * @return Une réponse HTTP indiquant le succès ou un message d'erreur.
     */
    @POST
    @Path("/signup/resident")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signUpResident(Resident resident) {
        if (residentRepository.find("email", resident.getEmail()).firstResult() != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("A resident with this email already exists.")
                    .build();
        }

        residentRepository.persist(resident);

        return Response.status(Response.Status.CREATED)
                .entity("Resident registered successfully")
                .build();
    }

    /**
     * Inscrit un nouvel intervenant.
     *
     * @param intervenant L'objet Intervenant à enregistrer.
     * @return Une réponse HTTP indiquant le succès ou un message d'erreur.
     */
    @POST
    @Path("/signup/intervenant")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signUpIntervenant(Intervenant intervenant) {
        if (intervenantRepository.find("email", intervenant.getEmail()).firstResult() != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Json.createObjectBuilder()
                            .add("message", "An intervenant with this email already exists.")
                            .build())
                    .build();
        }

        intervenantRepository.persist(intervenant);

        return Response.status(Response.Status.CREATED)
                .entity(Json.createObjectBuilder()
                        .add("message", "Intervenant registered successfully")
                        .build())
                .build();
    }
}
