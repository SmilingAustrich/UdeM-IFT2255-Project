package org.udem.ift2255.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.udem.ift2255.repository.EntraveRepository;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class EntraveService {

    @Inject
    EntraveRepository entraveRepository;

    public JsonArray getAllEntraves() {
        JsonObject data = entraveRepository.fetchEntraves();
        return data.getJsonObject("result").getJsonArray("records");
    }

    public List<JsonObject> getEntravesByWorkId(String workId) {
        return getAllEntraves()
                .stream()
                .map(entrave -> (JsonObject) entrave)  // Cast JsonValue to JsonObject
                .filter(entrave -> workId.equals(entrave.getString("id_request", null)))
                .collect(Collectors.toList());
    }

    public List<JsonObject> getEntravesByStreetName(String streetName) {
        return getAllEntraves()
                .stream()
                .map(entrave -> (JsonObject) entrave)  // Cast JsonValue to JsonObject
                .filter(entrave -> streetName.equalsIgnoreCase(entrave.getString("shortname", "")))
                .collect(Collectors.toList());
    }
}
