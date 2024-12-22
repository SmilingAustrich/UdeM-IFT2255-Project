/**
 * Classe EntraveRepository
 *
 * Cette classe est un dépôt (repository) pour interagir avec l'API des entraves de la ville de Montréal.
 * Elle permet de récupérer les données d'entraves à partir d'une API REST externe.
 * La classe est annotée avec @ApplicationScoped, ce qui signifie qu'une seule instance
 * sera créée pour toute la durée de l'application.
 */
package org.udem.ift2255.repository;

import jakarta.enterprise.context.ApplicationScoped; // Spécifie le scope de l'application
import jakarta.json.Json;                            // Fournit des outils pour créer et manipuler des objets JSON
import jakarta.json.JsonObject;                      // Représente un objet JSON
import jakarta.json.JsonReader;                      // Permet de lire et de parser une chaîne JSON

import java.io.BufferedReader;                       // Lecture tamponnée des flux d'entrée
import java.io.InputStreamReader;                    // Lecture des flux d'entrée
import java.io.StringReader;                         // Permet de lire une chaîne comme flux d'entrée
import java.net.HttpURLConnection;                   // Permet d'établir des connexions HTTP
import java.net.URL;                                 // Représente une URL

/**
 * Dépôt EntraveRepository
 *
 * Ce dépôt fournit une méthode pour récupérer les données d'entraves
 * à partir d'une API publique en utilisant HTTP.
 */
@ApplicationScoped
public class EntraveRepository {

    // URL de l'API des entraves
    private static final String ENTRAVE_API_URL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd";

    /**
     * Méthode fetchEntraves
     *
     * Cette méthode envoie une requête GET à l'API des entraves, récupère les données,
     * les parse en JSON, et retourne un objet JsonObject contenant la réponse.
     *
     * @return Un objet JsonObject contenant les données d'entraves.
     * @throws RuntimeException Si une erreur se produit lors de la requête ou du parsing.
     */
    public JsonObject fetchEntraves() {
        try {
            // Création de l'URL et ouverture de la connexion HTTP
            URL url = new URL(ENTRAVE_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // Définition de la méthode HTTP (GET)

            // Vérification du code de réponse HTTP
            int responseCode = connection.getResponseCode();
            System.out.println("HTTP Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Lecture de la réponse de l'API
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    System.out.println("Raw API Response: " + response);

                    // Parsing de la réponse JSON
                    try (JsonReader jsonReader = Json.createReader(new StringReader(response.toString()))) {
                        JsonObject jsonResponse = jsonReader.readObject();
                        System.out.println("Parsed JSON Response: " + jsonResponse);
                        return jsonResponse;
                    }
                }
            } else {
                // Lancer une exception en cas de réponse non valide
                throw new RuntimeException("Failed to fetch entraves. HTTP Response Code: " + responseCode);
            }
        } catch (Exception e) {
            // Gérer les exceptions en les affichant et en lançant une RuntimeException
            e.printStackTrace();
            throw new RuntimeException("Error fetching entraves from API", e);
        }
    }
}
