package org.udem.ift2255.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonReader;
import org.udem.ift2255.model.ResidentialWorkRequest;
import org.udem.ift2255.model.Resident;
import org.udem.ift2255.repository.ResidentRepository;
import org.udem.ift2255.repository.ResidentialWorkRequestRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


import jakarta.json.JsonArray;
import jakarta.json.JsonObject;


import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
    @Transactional
    public JsonArray rechercherTravaux(String searchCriteria, String searchValue) throws Exception {
        // Step 1: Fetch data from the API
        URL url = new URL("https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (JsonReader jsonReader = Json.createReader(new InputStreamReader(connection.getInputStream()))) {
                JsonObject jsonResponse = jsonReader.readObject();
                JsonObject result = jsonResponse.getJsonObject("result");
                JsonArray records = result.getJsonArray("records");

                // Step 2: Filter and format records
                jakarta.json.JsonArrayBuilder filteredRecords = Json.createArrayBuilder();

                for (JsonObject record : records.getValuesAs(JsonObject.class)) {
                    String valueToCheck = record.getString(searchCriteria, "").toLowerCase();
                    if (valueToCheck.contains(searchValue.toLowerCase())) {
                        JsonObject formattedRecord = Json.createObjectBuilder()
                                .add("ID du Travail", record.getString("_id", "N/A"))
                                .add("Quartier", record.getString("boroughid", "N/A"))
                                .add("Type de Travaux", record.getString("reason_category", "N/A"))
                                .add("Intervenant", record.getString("organizationname", "N/A"))
                                .build();
                        filteredRecords.add(formattedRecord);
                    }
                }

                return filteredRecords.build();
            }
        } else {
            throw new Exception("Failed to fetch data from API. Response Code: " + responseCode);
        }
    }


    private List<Map<String, String>> filterTravaux(jakarta.json.JsonArray travaux, String searchCriteria, String searchValue) {
        List<Map<String, String>> results = new ArrayList<>();

        for (int i = 0; i < travaux.size(); i++) {
            jakarta.json.JsonObject travail = travaux.getJsonObject(i); // Correct method for Jakarta JSON

            String valueToCheck = getAsStringSafe(travail.get(searchCriteria)); // Safely retrieve value
            if (valueToCheck != null && valueToCheck.toLowerCase().contains(searchValue.toLowerCase())) {
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

    private String getAsStringSafe(jakarta.json.JsonValue value) {
        return (value == null || value.getValueType() == jakarta.json.JsonValue.ValueType.NULL) ? "" : value.toString();
    }


    public JsonArray consulterEntraves() throws Exception {
        URL url = new URL("https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (JsonReader jsonReader = Json.createReader(new InputStreamReader(connection.getInputStream()))) {
                JsonObject jsonResponse = jsonReader.readObject();
                JsonObject result = jsonResponse.getJsonObject("result");
                JsonArray records = result.getJsonArray("records");

                // Create a new JsonArray builder to hold the formatted records
                jakarta.json.JsonArrayBuilder formattedRecords = Json.createArrayBuilder();

                // Iterate through the records and extract necessary fields
                for (JsonObject record : records.getValuesAs(JsonObject.class)) {
                    JsonObject formattedRecord = Json.createObjectBuilder()
                            .add("ID du Travail", record.getString("_id", "N/A")) // Replace "_id" with actual field name
                            .add("Rue", record.getString("Rue", "N/A"))         // Replace "Rue" with actual field name
                            .add("Impact", record.getString("Impact", "N/A"))   // Replace "Impact" with actual field name
                            .build();
                    formattedRecords.add(formattedRecord);
                }

                return formattedRecords.build();
            }
        } else {
            throw new Exception("Failed to fetch data from API. Response Code: " + responseCode);
        }
    }

    }
