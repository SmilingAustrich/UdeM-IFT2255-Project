package com.maville.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads the city of Montreal's open data portal over CKAN's datastore_search.
 *
 * <p>The same open-connection, read-body, parse-JSON sequence used to appear
 * three times inside {@code Resident}, once per screen that needed data, with
 * the response handling written slightly differently each time. It lives here
 * once.
 *
 * <p>Parsing is separated from fetching on purpose: {@link #parseWork(String)}
 * and {@link #parseObstructions(String)} are static and take a string, so the
 * field mapping can be tested without a network.
 */
public final class MontrealOpenData implements OpenDataSource {

    private static final String ENDPOINT =
            "https://donnees.montreal.ca/api/3/action/datastore_search";

    /** CKAN resource ids for the two datasets the application reads. */
    public static final String WORK_RESOURCE_ID = "cc41b532-f12d-40fb-9f55-eb58c9a2b12b";
    public static final String OBSTRUCTION_RESOURCE_ID = "a2bc8014-488c-495d-941b-e7ae1999d1bd";

    private final int timeoutMillis;

    public MontrealOpenData() {
        this(10_000);
    }

    public MontrealOpenData(int timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    @Override
    public List<WorkRecord> fetchWork() throws IOException {
        return parseWork(get(WORK_RESOURCE_ID));
    }

    @Override
    public List<ObstructionRecord> fetchObstructions() throws IOException {
        return parseObstructions(get(OBSTRUCTION_RESOURCE_ID));
    }

    public static List<WorkRecord> parseWork(String json) {
        List<WorkRecord> works = new ArrayList<>();
        for (JsonElement element : records(json)) {
            JsonObject record = element.getAsJsonObject();
            works.add(new WorkRecord(
                    string(record, "id"),
                    string(record, "boroughid"),
                    string(record, "reason_category"),
                    string(record, "organizationname")));
        }
        return works;
    }

    public static List<ObstructionRecord> parseObstructions(String json) {
        List<ObstructionRecord> obstructions = new ArrayList<>();
        for (JsonElement element : records(json)) {
            JsonObject record = element.getAsJsonObject();
            obstructions.add(new ObstructionRecord(
                    string(record, "id_request"),
                    string(record, "shortname"),
                    string(record, "streetimpacttype")));
        }
        return obstructions;
    }

    /** CKAN wraps the rows in {@code {"result": {"records": [...]}}}. */
    private static JsonArray records(String json) {
        JsonObject body = JsonParser.parseString(json).getAsJsonObject();
        JsonObject result = body.getAsJsonObject("result");
        if (result == null) {
            return new JsonArray();
        }
        JsonArray records = result.getAsJsonArray("records");
        return records == null ? new JsonArray() : records;
    }

    /**
     * A field the portal omits, or sends as JSON null, reads as "N/A". The
     * screens print these values straight out, so they must never be null.
     */
    private static String string(JsonObject record, String field) {
        JsonElement value = record.get(field);
        return value != null && !value.isJsonNull() ? value.getAsString() : "N/A";
    }

    private String get(String resourceId) throws IOException {
        URL url = URI.create(ENDPOINT + "?resource_id=" + resourceId).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(timeoutMillis);
        connection.setReadTimeout(timeoutMillis);

        try {
            int status = connection.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                throw new IOException(
                        "L'API de la ville de Montreal a repondu " + status + " pour " + resourceId);
            }
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder body = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    body.append(line);
                }
                return body.toString();
            }
        } finally {
            connection.disconnect();
        }
    }
}
