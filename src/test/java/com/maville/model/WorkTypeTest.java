package com.maville.model;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The list of work categories used to exist in three copies that had drifted
 * apart. These check the single copy holds together.
 */
public class WorkTypeTest {

    @Test
    public void tenTypesAreOfferedNumberedOneToTen() {
        assertEquals(10, WorkType.values().length);
        for (WorkType type : WorkType.values()) {
            assertEquals(type, WorkType.fromMenuNumber(type.menuNumber()).orElseThrow());
        }
    }

    @Test
    public void numbersOutsideTheMenuResolveToNothing() {
        assertTrue(WorkType.fromMenuNumber(0).isEmpty());
        assertTrue(WorkType.fromMenuNumber(11).isEmpty());
        assertTrue(WorkType.fromMenuNumber(-1).isEmpty());
    }

    /**
     * A request submitted through one screen has to be findable through
     * another. That held only if every label is distinct and round trips.
     */
    @Test
    public void everyLabelIsDistinctAndRoundTrips() {
        Set<String> labels = new HashSet<>();
        for (WorkType type : WorkType.values()) {
            assertTrue("label en double: " + type.label(), labels.add(type.label()));
            assertEquals(type, WorkType.fromLabel(type.label()).orElseThrow());
        }
    }

    /**
     * Option 8 was "Travaux residentiel" on the search screen and "Travaux
     * residentiels" on the submission screen, so a residential request could be
     * submitted and then never found.
     */
    @Test
    public void optionEightIsResidentialWorkUnderOneSpelling() {
        assertEquals(WorkType.RESIDENTIEL, WorkType.fromMenuNumber(8).orElseThrow());
        assertEquals("Travaux résidentiels", WorkType.RESIDENTIEL.label());
    }

    @Test
    public void labelLookupIgnoresCase() {
        assertEquals(WorkType.ROUTIER, WorkType.fromLabel("TRAVAUX ROUTIERS").orElseThrow());
        assertTrue(WorkType.fromLabel("Travaux inexistants").isEmpty());
    }

    @Test
    public void labelLookupDoesNotMatchOnAPrefix() {
        assertFalse(WorkType.fromLabel("Travaux").isPresent());
    }
}
