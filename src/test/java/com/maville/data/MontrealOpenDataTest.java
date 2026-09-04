package com.maville.data;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Parsing, without a network.
 *
 * <p>The only coverage this code had before was a test that called the live
 * city API, so it failed on a machine with no route to donnees.montreal.ca and
 * told you nothing about the field mapping when it did pass. The JSON below is
 * shaped like a CKAN datastore_search response; the live call is covered
 * separately by {@link MontrealOpenDataIT}.
 */
public class MontrealOpenDataTest {

    private static final String TWO_WORKS = """
            {"result": {"records": [
              {"id": "R-1", "boroughid": "Le Plateau-Mont-Royal",
               "reason_category": "Travaux routiers", "organizationname": "Ville de Montreal"},
              {"id": "R-2", "boroughid": "Rosemont",
               "reason_category": "Travaux souterrains", "organizationname": "Energir"}
            ]}}""";

    private static final String ONE_OBSTRUCTION = """
            {"result": {"records": [
              {"id_request": "R-1", "shortname": "Rue Saint-Denis",
               "streetimpacttype": "Fermeture complete"}
            ]}}""";

    @Test
    public void readsEveryWorkRecord() {
        List<WorkRecord> works = MontrealOpenData.parseWork(TWO_WORKS);

        assertEquals(2, works.size());
        assertEquals(new WorkRecord("R-1", "Le Plateau-Mont-Royal",
                "Travaux routiers", "Ville de Montreal"), works.get(0));
        assertEquals("Energir", works.get(1).organization());
    }

    @Test
    public void readsObstructionRecords() {
        List<ObstructionRecord> obstructions = MontrealOpenData.parseObstructions(ONE_OBSTRUCTION);

        assertEquals(1, obstructions.size());
        assertEquals(new ObstructionRecord("R-1", "Rue Saint-Denis", "Fermeture complete"),
                obstructions.get(0));
    }

    /** The screens print these values straight out, so none may be null. */
    @Test
    public void aMissingFieldReadsAsNotAvailable() {
        List<WorkRecord> works = MontrealOpenData.parseWork(
                """
                {"result": {"records": [{"id": "R-3", "reason_category": null}]}}""");

        WorkRecord work = works.get(0);
        assertEquals("R-3", work.id());
        assertEquals("N/A", work.borough());
        assertEquals("N/A", work.category());
        assertEquals("N/A", work.organization());
    }

    @Test
    public void anEmptyResultIsAnEmptyList() {
        assertTrue(MontrealOpenData.parseWork("{\"result\": {\"records\": []}}").isEmpty());
    }

    /** A CKAN error response carries no result object at all. */
    @Test
    public void aResponseWithoutResultIsAnEmptyList() {
        assertTrue(MontrealOpenData.parseWork("{\"success\": false}").isEmpty());
        assertTrue(MontrealOpenData.parseObstructions("{\"success\": false}").isEmpty());
    }

    @Test
    public void boroughMatchingIsPartialAndCaseInsensitive() {
        WorkRecord work = MontrealOpenData.parseWork(TWO_WORKS).get(0);

        assertTrue(work.isInBorough("plateau"));
        assertTrue(work.isInBorough("Le Plateau-Mont-Royal"));
        assertFalse(work.isInBorough("Verdun"));
    }

    /** Category is an exact match, so a borough name must not select on it. */
    @Test
    public void categoryMatchingIsExactButCaseInsensitive() {
        WorkRecord work = MontrealOpenData.parseWork(TWO_WORKS).get(0);

        assertTrue(work.isOfCategory("travaux routiers"));
        assertFalse(work.isOfCategory("Travaux"));
    }

    @Test
    public void obstructionsJoinBackToTheirWork() {
        ObstructionRecord obstruction = MontrealOpenData.parseObstructions(ONE_OBSTRUCTION).get(0);

        assertTrue(obstruction.belongsToWork("r-1"));
        assertFalse(obstruction.belongsToWork("R-2"));
        assertTrue(obstruction.isOnStreet("rue saint-denis"));
    }
}
