package com.maville.data;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Checks the two datasets still carry the fields this application reads.
 *
 * <p>Named *IT so it runs under failsafe (`mvn verify`) and not under surefire
 * (`mvn test`). It needs a route to donnees.montreal.ca, and it fails when the
 * city changes a column name, which is a real signal but not one that should
 * break an offline build.
 */
public class MontrealOpenDataIT {

    private final MontrealOpenData openData = new MontrealOpenData();

    @Test
    public void theWorkDatasetStillHasTheFieldsWeRead() throws IOException {
        List<WorkRecord> works = openData.fetchWork();

        assertNotNull(works);
        assertFalse("Le jeu de données des travaux est vide", works.isEmpty());
        for (WorkRecord work : works) {
            assertNotNull(work.id());
            assertNotNull(work.borough());
            assertNotNull(work.category());
            assertNotNull(work.organization());
        }
    }

    @Test
    public void theObstructionDatasetStillHasTheFieldsWeRead() throws IOException {
        List<ObstructionRecord> obstructions = openData.fetchObstructions();

        assertNotNull(obstructions);
        assertFalse("Le jeu de données des entraves est vide", obstructions.isEmpty());
        for (ObstructionRecord obstruction : obstructions) {
            assertNotNull(obstruction.workId());
            assertNotNull(obstruction.street());
            assertNotNull(obstruction.impact());
        }
    }
}
