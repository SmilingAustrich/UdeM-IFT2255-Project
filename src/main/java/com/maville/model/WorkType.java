package com.maville.model;

import java.util.Optional;

/**
 * The ten categories of work a resident can pick from.
 *
 * <p>This list was written out three times: once as a numbered menu in the
 * search screen, once as a numbered menu in the submission screen, and once
 * more as a {@code switch} mapping the number back to a label. The three
 * copies had drifted. Option 8 was "Travaux residentiel" in the search screen
 * and "Travaux residentiels" in the submission screen, so a request submitted
 * through one screen could not be found through the other. Holding the list in
 * one place removes that class of bug.
 */
public enum WorkType {

    ROUTIER("Travaux routiers"),
    GAZ_ELECTRICITE("Travaux de gaz ou électricité"),
    CONSTRUCTION("Construction ou rénovation"),
    PAYSAGER("Entretien paysager"),
    TRANSPORT_COMMUN("Travaux liés aux transports en commun"),
    SIGNALISATION("Travaux de signalisation et éclairage"),
    SOUTERRAIN("Travaux souterrains"),
    RESIDENTIEL("Travaux résidentiels"),
    URBAIN("Entretien urbain"),
    TELECOM("Entretien des réseaux de télécommunication");

    private final String label;

    WorkType(String label) {
        this.label = label;
    }

    /** The wording shown to the user and stored on a request. */
    public String label() {
        return label;
    }

    /** The number this type is offered under, counting from 1. */
    public int menuNumber() {
        return ordinal() + 1;
    }

    /**
     * Resolves a menu selection. Empty when the number is outside 1 to 10, so
     * each screen keeps its own wording for an invalid choice.
     */
    public static Optional<WorkType> fromMenuNumber(int number) {
        if (number < 1 || number > values().length) {
            return Optional.empty();
        }
        return Optional.of(values()[number - 1]);
    }

    /** Resolves a stored label back to a type, ignoring case. */
    public static Optional<WorkType> fromLabel(String label) {
        for (WorkType type : values()) {
            if (type.label.equalsIgnoreCase(label)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }
}
