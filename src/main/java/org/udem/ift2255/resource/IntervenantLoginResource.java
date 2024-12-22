/**
 * Classe IntervenantLoginResource
 *
 * Cette classe représente une ressource REST pour gérer l'authentification
 * des intervenants dans l'application Ma Ville. Elle utilise le service
 * AuthenticationService pour valider les informations de connexion fournies.
 */
package org.udem.ift2255.resource;

import jakarta.ws.rs.*;                                // Fournit les annotations pour les services REST
import jakarta.ws.rs.core.Response;                    // Fournit des outils pour construire les réponses HTTP
import org.udem.ift2255.dto.LoginRequestDTO;           // DTO pour les requêtes de connexion
import org.udem.ift2255.service.AuthenticationService; // Service d'authentification

/**
 * Ressource REST pour l'authentification des intervenants.
 */
@Path("/authenticate")
public class IntervenantLoginResource {

    private final AuthenticationService authenticationService; // Service pour gérer l'authentification

    /**
     * Constructeur avec injection du service AuthenticationService.
     *
     * @param authenticationService Le service d'authentification injecté.
     */
    public IntervenantLoginResource(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Authentifie un intervenant avec les informations fournies.
     *
     * @param loginRequest Les informations de connexion (email et mot de passe).
     * @return Une réponse HTTP indiquant le succès ou l'échec de l'authentification.
     */
    @POST
    @Path("/intervenant")
    @Consumes("application/json") // Accepte du contenu JSON
    public Response authenticateIntervenant(LoginRequestDTO loginRequest) {
        boolean isAuthenticated = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword(), "intervenant");

        if (isAuthenticated) {
            return Response.ok().build(); // Succès de l'authentification
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build(); // Échec de l'authentification
        }
    }
}
