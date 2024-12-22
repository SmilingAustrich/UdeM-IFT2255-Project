/**
 * Classe EntraveResource
 *
 * Cette classe représente une ressource REST pour gérer les entraves.
 * Elle fournit des points d'entrée pour récupérer toutes les entraves, filtrer par ID de travaux,
 * ou rechercher par nom de rue. La pagination est également prise en charge.
 */
package org.udem.ift2255.resource;

import jakarta.inject.Inject;                          // Permet d'injecter des dépendances
import jakarta.json.*;                                 // Fournit des outils pour créer et manipuler des objets JSON
import jakarta.ws.rs.*;                                // Fournit les annotations pour les services REST
import jakarta.ws.rs.core.MediaType;                   // Spécifie les types de contenu pour les requêtes et réponses
import jakarta.ws.rs.core.Response;                    // Fournit des outils pour construire les réponses HTTP
import org.udem.ift2255.service.EntraveService;        // Service pour gérer les entraves

import java.util.List;                                 // Permet de manipuler des listes

/**
 * Ressource REST pour la gestion des entraves
 */
@Path("/entraves")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EntraveResource {

    @Inject
    EntraveService entraveService; // Service pour récupérer les données d'entraves

    /**
     * Récupère toutes les entraves avec pagination.
     *
     * @param page  Le numéro de la page à récupérer (par défaut : 1).
     * @param limit Le nombre d'entraves par page (par défaut : 10).
     * @return Une réponse HTTP contenant les entraves paginées ou un message d'erreur.
     */
    @GET
    @Path("/getAllEntraves")
    public Response getAllEntraves(@QueryParam("page") @DefaultValue("1") int page,
                                   @QueryParam("limit") @DefaultValue("10") int limit) {
        try {
            JsonArray entraves = entraveService.getAllEntraves();

            int total = entraves.size();
            int start = (page - 1) * limit;
            int end = Math.min(start + limit, total);

            System.out.println("Pagination - Start: " + start + ", End: " + end + ", Total: " + total);

            if (start >= total) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Page number exceeds total available pages.")
                        .build();
            }

            List<JsonValue> paginatedEntraves = entraves.subList(start, end);

            JsonArrayBuilder resultsArray = Json.createArrayBuilder();
            for (JsonValue entrave : paginatedEntraves) {
                resultsArray.add(entrave);
            }

            JsonObject response = Json.createObjectBuilder()
                    .add("results", resultsArray)
                    .add("total", total)
                    .add("page", page)
                    .add("limit", limit)
                    .build();

            System.out.println("Paginated Response: " + response);

            return Response.ok(response).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while processing the request.")
                    .build();
        }
    }

    /**
     * Récupère les entraves associées à un ID de travaux donné.
     *
     * @param workId L'ID des travaux.
     * @return Une réponse contenant les entraves correspondantes ou un message d'erreur.
     */
    @GET
    @Path("/by-work-id/{workId}")
    public Response getEntravesByWorkId(@PathParam("workId") String workId) {
        List<JsonObject> entraves = entraveService.getEntravesByWorkId(workId);
        return entraves.isEmpty() ? Response.status(Response.Status.NOT_FOUND).build()
                : Response.ok(entraves).build();
    }

    /**
     * Récupère les entraves pour une rue donnée.
     *
     * @param streetName Le nom de la rue.
     * @return Une réponse contenant les entraves correspondantes ou un message d'erreur.
     */
    @GET
    @Path("/by-street/{streetName}")
    public Response getEntravesByStreet(@PathParam("streetName") String streetName) {
        List<JsonObject> entraves = entraveService.getEntravesByStreetName(streetName);
        return entraves.isEmpty() ? Response.status(Response.Status.NOT_FOUND).build()
                : Response.ok(entraves).build();
    }
}
