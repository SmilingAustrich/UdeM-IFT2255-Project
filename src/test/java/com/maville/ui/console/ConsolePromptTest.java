package com.maville.ui.console;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The registration rules.
 *
 * <p>These were private methods on the 579 line Menu class, so nothing could
 * reach them. Extracting them made them reachable; this is what they do.
 */
public class ConsolePromptTest {

    @Test
    public void acceptsOrdinaryEmailAddresses() {
        assertTrue(ConsolePrompt.isValidEmail("tarik@example.com"));
        assertTrue(ConsolePrompt.isValidEmail("first.last+tag@sub.domain.ca"));
        assertTrue(ConsolePrompt.isValidEmail("a_b-c%d@example.co.uk"));
    }

    @Test
    public void rejectsMalformedEmailAddresses() {
        assertFalse(ConsolePrompt.isValidEmail(""));
        assertFalse(ConsolePrompt.isValidEmail(null));
        assertFalse(ConsolePrompt.isValidEmail("no-at-sign.com"));
        assertFalse(ConsolePrompt.isValidEmail("@example.com"));
        assertFalse(ConsolePrompt.isValidEmail("user@"));
        assertFalse(ConsolePrompt.isValidEmail("user@example"));
        assertFalse(ConsolePrompt.isValidEmail("user @example.com"));
    }

    @Test
    public void aCityIdIsExactlyEightDigits() {
        assertTrue(ConsolePrompt.isValidCityId("12345678"));
        assertFalse(ConsolePrompt.isValidCityId("1234567"));
        assertFalse(ConsolePrompt.isValidCityId("123456789"));
        assertFalse(ConsolePrompt.isValidCityId("1234567a"));
        assertFalse(ConsolePrompt.isValidCityId(""));
        assertFalse(ConsolePrompt.isValidCityId(null));
    }

    @Test
    public void parsesDayMonthYear() {
        assertEquals(LocalDate.of(2024, 11, 20),
                ConsolePrompt.parseDate("20/11/2024").orElseThrow());
    }

    /**
     * The shape is checked before parsing. Without that, a single digit day or
     * month parses and the user never learns the format they were asked for.
     */
    @Test
    public void requiresTwoDigitDayAndMonthAndFourDigitYear() {
        assertTrue(ConsolePrompt.parseDate("1/11/2024").isEmpty());
        assertTrue(ConsolePrompt.parseDate("01/1/2024").isEmpty());
        assertTrue(ConsolePrompt.parseDate("01/11/24").isEmpty());
        assertTrue(ConsolePrompt.parseDate("2024-11-01").isEmpty());
        assertTrue(ConsolePrompt.parseDate("").isEmpty());
        assertTrue(ConsolePrompt.parseDate(null).isEmpty());
    }

    @Test
    public void rejectsADateThatLooksRightButDoesNotExist() {
        assertTrue(ConsolePrompt.parseDate("31/02/2024").isEmpty());
        assertTrue(ConsolePrompt.parseDate("32/01/2024").isEmpty());
        assertTrue(ConsolePrompt.parseDate("01/13/2024").isEmpty());
    }

    @Test
    public void ageIsCompletedYears() {
        LocalDate today = LocalDate.of(2024, 11, 20);
        assertEquals(30, ConsolePrompt.ageOn(LocalDate.of(1994, 11, 20), today));
        assertEquals(30, ConsolePrompt.ageOn(LocalDate.of(1994, 1, 1), today));
    }

    /** The day before a birthday the user is still a year younger. */
    @Test
    public void ageDoesNotRoundUpToTheNextBirthday() {
        LocalDate today = LocalDate.of(2024, 11, 20);
        assertEquals(29, ConsolePrompt.ageOn(LocalDate.of(1994, 11, 21), today));
        assertEquals(14, ConsolePrompt.ageOn(LocalDate.of(2009, 11, 21), today));
        assertEquals(16, ConsolePrompt.ageOn(LocalDate.of(2008, 11, 20), today));
    }

    /** A 29 February birthday has no exact anniversary in a common year. */
    @Test
    public void handlesALeapDayBirthday() {
        assertEquals(4, ConsolePrompt.ageOn(LocalDate.of(2020, 2, 29), LocalDate.of(2025, 2, 28)));
        assertEquals(5, ConsolePrompt.ageOn(LocalDate.of(2020, 2, 29), LocalDate.of(2025, 3, 1)));
    }
}
