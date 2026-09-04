package com.maville.data;

/**
 * One road work entry from the city of Montreal's "travaux" dataset.
 *
 * <p>Only the four fields the application actually shows are kept. A missing
 * field arrives as {@code "N/A"} rather than {@code null}, which is what the
 * screens printed before this record existed.
 */
public record WorkRecord(String id, String borough, String category, String organization) {

    /** True when any of the shown fields contains {@code term}, ignoring case. */
    public boolean matches(String term) {
        String needle = term.toLowerCase();
        return id.toLowerCase().contains(needle)
                || borough.toLowerCase().contains(needle)
                || category.toLowerCase().contains(needle)
                || organization.toLowerCase().contains(needle);
    }

    public boolean isInBorough(String boroughName) {
        return borough.toLowerCase().contains(boroughName.toLowerCase());
    }

    public boolean isOfCategory(String categoryName) {
        return category.equalsIgnoreCase(categoryName);
    }
}
