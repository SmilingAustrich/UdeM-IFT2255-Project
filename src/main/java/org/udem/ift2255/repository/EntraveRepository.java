package org.udem.ift2255.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

@ApplicationScoped
public class EntraveRepository {

    private static final String ENTRAVE_API_URL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd";

    public JsonObject fetchEntraves() {
        try {
            URL url = new URL(ENTRAVE_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("HTTP Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    System.out.println("Raw API Response: " + response);

                    // Use Jakarta JSON to parse the response
                    try (JsonReader jsonReader = Json.createReader(new StringReader(response.toString()))) {
                        JsonObject jsonResponse = jsonReader.readObject();
                        System.out.println("Parsed JSON Response: " + jsonResponse);
                        return jsonResponse;
                    }
                }
            } else {
                throw new RuntimeException("Failed to fetch entraves. HTTP Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching entraves from API", e);
        }
    }
}
