package com.maville.data;

import java.io.IOException;
import java.util.List;

/**
 * Where road work and street obstructions come from.
 *
 * <p>The console screens depend on this interface rather than on
 * {@code HttpURLConnection} directly, so they can be driven from a fixed list
 * of records in a test instead of the live city API.
 */
public interface OpenDataSource {

    List<WorkRecord> fetchWork() throws IOException;

    List<ObstructionRecord> fetchObstructions() throws IOException;
}
