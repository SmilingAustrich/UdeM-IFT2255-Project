package org.udem.ift2255.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonReader;
import org.udem.ift2255.model.ResidentialWorkRequest;
import org.udem.ift2255.model.Resident;
import org.udem.ift2255.repository.ResidentialWorkRequestRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.time.LocalDate;


@ApplicationScoped
public class ResidentService {

    @Inject
    ResidentialWorkRequestRepository workRequestRepository;


    /**
     * Permet au résident de soumettre une requête de travail.
     */
    @Transactional
    public void soumettreRequeteTravail(Resident resident, String workTitle, String detailedWorkDescription,
                                        String quartier, String workType, LocalDate workWishedStartDate) {
        // Ensure that the start date is not in the past
        if (workWishedStartDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La date ne peut pas être dans le passé.");
        }



        // Create a new work request
        ResidentialWorkRequest request = new ResidentialWorkRequest(
                resident, workTitle, detailedWorkDescription, workType, workWishedStartDate, quartier
        );
        // Save the work request to the repository
        workRequestRepository.saveRequest(request);
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
