/**
 * Classe ResidentAPI
 *
 * Cette classe expose des endpoints REST pour l'application.
 * Elle utilise la classe ResidentService afin de récupérer des données de type entraves
 * et les renvoie sous forme de réponses HTTP.
 */
package org.udem.ift2255.api;

import jakarta.ws.rs.GET;        // Importation de l’annotation GET (pour définir des endpoints de lecture)
import jakarta.ws.rs.Path;       // Importation de l’annotation Path (pour définir les chemins d’accès de l’endpoint)
import jakarta.ws.rs.Produces;   // Importation de l’annotation Produces (pour définir le type MIME de la réponse)
import jakarta.ws.rs.core.Response;  // Importation de la classe Response (pour créer des réponses HTTP)
import jakarta.json.JsonArray;       // Pour manipuler les tableaux JSON
import jakarta.json.JsonObject;      // Pour manipuler les objets JSON
import jakarta.json.JsonValue;       // Représente une valeur JSON
import org.udem.ift2255.service.ResidentService; // Import du service qui gère la logique métier

import java.util.List;               // Importation de la classe List (structure de données)
import java.util.Map;                // Importation de la classe Map (structure de données)
import java.util.stream.Collectors;  // Pour la manipulation des flux de données (Stream API)

/**
 * La classe ResidentAPI offre différents endpoints sous le chemin /api.
 */
@Path("/api")  // Définit le chemin de base pour tous les endpoints de cette classe
public class ResidentAPI {

    // Déclare une instance de ResidentService pour communiquer avec la couche métier
    private ResidentService residentService = new ResidentService();

    /**
     * Méthode pour consulter les données des entraves.
     *
     * @return Response La réponse HTTP contenant la liste des entraves en JSON ou une erreur.
     */
    @GET
    @Path("/entraves")              // Spécifie que l’endpoint aura le chemin /api/entraves
    @Produces("application/json")   // Indique que la réponse sera au format JSON
    public Response consulterEntraves() {
        try {
            // Récupère la liste des entraves grâce à la méthode consulterEntraves() du ResidentService
            JsonArray entravesJsonArray = residentService.consulterEntraves();

            // Convertit le JsonArray en List<Map<String, Object>> pour simplifier la manipulation en Java
            List<Map<String, Object>> entravesList = entravesJsonArray.stream()
                    .filter(jsonValue -> jsonValue.getValueType() == JsonValue.ValueType.OBJECT) // Filtre pour ne garder que les JsonObject
                    .map(jsonValue -> (JsonObject) jsonValue) // Cast vers JsonObject
                    .map(jsonObject -> jsonObject.entrySet().stream()
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey,
                                    entry -> parseJsonValue(entry.getValue()) // Conversion du JsonValue en objet Java
                            )))
                    .collect(Collectors.toList());

            // Retourne une réponse HTTP 200 (OK) avec la liste des entraves
            return Response.ok(entravesList).build();

        } catch (Exception e) {
            // En cas d'erreur, retourne une réponse HTTP 500 (Internal Server Error) avec un message explicatif
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur lors de la récupération des entraves: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Méthode utilitaire pour convertir un JsonValue en objet Java (String, Boolean, Number, etc.).
     *
     * @param value Le JsonValue à convertir
     * @return Object Retourne la valeur convertie (String, Boolean, Number, null, etc.)
     * @throws IllegalArgumentException Levée si le type de JsonValue n’est pas géré
     */
    private Object parseJsonValue(JsonValue value) {
        // Selon le type de la valeur JSON, on renvoie l’objet Java approprié
        switch (value.getValueType()) {
            case STRING:
                // Retourne la chaîne de caractères sans les guillemets
                return value.toString().replace("\"", "");
            case NUMBER:
                // Retourne la valeur numérique sous forme de String
                // (il serait possible de convertir en entier ou double selon le besoin)
                return value.toString();
            case TRUE:
                // Retourne la valeur booléenne vraie
                return true;
            case FALSE:
                // Retourne la valeur booléenne fausse
                return false;
            case NULL:
                // Retourne la valeur Java null
                return null;
            case OBJECT:
                // Retourne l'objet JSON tel quel
                return value;
            case ARRAY:
                // Retourne le tableau JSON tel quel
                return value;
            default:
                // Si le type n'est pas géré, lance une exception
                throw new IllegalArgumentException("Unsupported JSON value type: " + value.getValueType());
        }
    }
}
