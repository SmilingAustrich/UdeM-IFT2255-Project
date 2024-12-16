package org.udem.ift2255.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import org.udem.ift2255.repository.EntraveRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class EntraveService {

    @Inject
    EntraveRepository entraveRepository;

    public JsonArray getAllEntraves() {
        JsonObject data = entraveRepository.fetchEntraves();
        JsonObject result = data.getJsonObject("result");
        if (result == null) {
            throw new IllegalStateException("No 'result' found in fetched data");
        }
        return result.getJsonArray("records");
    }

    public List<JsonObject> getEntravesByWorkId(String workId) {
        return getAllEntraves().stream()
                .filter(entrave -> entrave.getValueType() == JsonValue.ValueType.OBJECT)
                .map(entrave -> (JsonObject) entrave)
                .filter(entrave -> workId.equals(entrave.getString("id_request", null)))
                .collect(Collectors.toList());
    }

    public List<JsonObject> getEntravesByStreetName(String streetName) {
        return getAllEntraves().stream()
                .filter(entrave -> entrave.getValueType() == JsonValue.ValueType.OBJECT)
                .map(entrave -> (JsonObject) entrave)
                .filter(entrave -> streetName.equalsIgnoreCase(entrave.getString("shortname", "")))
                .collect(Collectors.toList());
    }
}
