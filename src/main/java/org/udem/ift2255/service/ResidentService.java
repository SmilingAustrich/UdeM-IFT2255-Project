package org.udem.ift2255.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonReader;
import org.udem.ift2255.model.ResidentialWorkRequest;
import org.udem.ift2255.model.Resident;
import org.udem.ift2255.repository.ResidentRepository;
import org.udem.ift2255.repository.ResidentialWorkRequestRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

@ApplicationScoped
public class ResidentService {

    @Inject
    ResidentialWorkRequestRepository workRequestRepository;
    @Inject
    ResidentRepository residentRepository; // Inject the repository if you use one (e.g., to fetch from DB)


    // Authenticate the resident based on email and password
    public boolean authenticate(String email, String password) {
        // Here, assume we have a method that fetches the resident by email from a repository or test data
        Resident resident = residentRepository.findByEmail(email);

        // Check if the resident exists and the password matches
        if (resident != null && resident.getPassword().equals(password)) {
            return true; // Authentication successful
        }
        return false; // Authentication failed
    }

    /**
     * Permet au résident de soumettre une requête de travail.
     */
    @Transactional
    public void soumettreRequeteTravail(Resident resident, String workTitle, String detailedWorkDescription,
                                        String quartier, int workType, LocalDate workWishedStartDate) {
        // Ensure that the start date is not in the past
        if (workWishedStartDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La date ne peut pas être dans le passé.");
        }

        // Determine the work type string
        String workTypeString = getWorkTypeString(workType);

        // Create a new work request
        ResidentialWorkRequest request = new ResidentialWorkRequest(
                resident, workTitle, detailedWorkDescription, workTypeString, workWishedStartDate, quartier
        );
        // Save the work request to the repository
        workRequestRepository.saveRequest(request);
    }

    private String getWorkTypeString(int workType) {
        switch (workType) {
            case 1:
                return "Travaux routiers";
            case 2:
                return "Travaux de gaz ou électricité";
            case 3:
                return "Construction ou rénovation";
            case 4:
                return "Entretien paysager";
            case 5:
                return "Travaux liés aux transports en commun";
            case 6:
                return "Travaux de signalisation et éclairage";
            case 7:
                return "Travaux souterrains";
            case 8:
                return "Travaux résidentiels";
            case 9:
                return "Entretien urbain";
            case 10:
                return "Entretien des réseaux de télécommunication";
            default:
                return "Inconnu";
        }
    }

    @Transactional
    public void fermerRequete(ResidentialWorkRequest requete) {
        // Check if the work request is available before closing it
        if (!requete.isWorkAvailable()) {
            // Optional: Use logging instead of System.out.println for production code
            // logger.info("La requête est fermée par " + requete.getResident().getFirstName());
            workRequestRepository.removeRequest(requete);
        }
    }

    // If you still want a 'creerRequete' method, here's how it could be rewritten.
    @Transactional
    public void creerRequete(Resident resident, String workTitle, String detailedWorkDescription,
                             String workType, LocalDate workWishedStartDate, String quartier) {
        ResidentialWorkRequest requete = new ResidentialWorkRequest(
                resident, workTitle, detailedWorkDescription, workType, workWishedStartDate, quartier
        );
        workRequestRepository.saveRequest(requete);
    }

    public static List<Map<String, String>> rechercherTravaux() {
        List<Map<String, String>> travauxList = new ArrayList<>();

        try {
            // Step 1: Fetch data from API
            URL url = new URL("https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Step 2: Parse the JSON response
                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                JsonArray travaux = jsonResponse.getAsJsonObject("result").getAsJsonArray("records");

                // Step 3: Store the search results in the list (instead of printing them)
                boolean continueSearching = true;
                while (continueSearching) {
                    // This part is for searching criteria (could be handled by a separate method)
                    // Here we're just assuming the search criteria are based on title for simplicity

                    // Example for searching by title
                    String titre = "Example Title"; // This should be dynamically input from the user
                    List<Map<String, String>> foundTravaux = searchByTitle(travaux, titre);
                    travauxList.addAll(foundTravaux);

                    // In a real application, handle other search criteria (type, borough, etc.) here
                    continueSearching = false;  // Exit loop after one search for simplicity
                }
            } else {
                // Handle the error (e.g., log it or throw an exception)
                throw new Exception("[ERREUR] Impossible de récupérer les données.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return travauxList;
    }

    private static List<Map<String, String>> searchByTitle(JsonArray travaux, String titre) {
        List<Map<String, String>> results = new ArrayList<>();

        for (int i = 0; i < travaux.size(); i++) {
            JsonObject travail = travaux.get(i).getAsJsonObject();
            if (getAsStringSafe(travail.get("id")).contains(titre)) {
                Map<String, String> travailData = Map.of(
                        "id", getAsStringSafe(travail.get("id")),
                        "quartier", getAsStringSafe(travail.get("boroughid")),
                        "typeTravaux", getAsStringSafe(travail.get("reason_category")),
                        "intervenant", getAsStringSafe(travail.get("organizationname"))
                );
                results.add(travailData);
            }
        }

        return results;
    }

    private static String getAsStringSafe(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    public JsonArray convertListToJsonArray(List<Map<String, Object>> list) {
        Gson gson = new Gson();
        JsonArray jsonArray = new JsonArray();

        for (Map<String, Object> map : list) {
            JsonElement jsonElement = gson.toJsonTree(map);
            jsonArray.add(jsonElement);
        }

        return jsonArray;}


    // Method to fetch the data from external API using Gson
    public JsonArray consulterEntraves() throws Exception {
        URL url = new URL("https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream())) {
                // Parse the JSON response using Gson
                JsonObject jsonResponse = JsonParser.parseReader(inputStreamReader).getAsJsonObject();
                return jsonResponse.getAsJsonObject("result").getAsJsonArray("records");
            }
        } else {
            throw new Exception("Failed to fetch data from API. Response Code: " + responseCode);
        }
    }

    }
