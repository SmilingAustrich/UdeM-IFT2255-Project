package com.maville.data;

/**
 * One street obstruction from the city of Montreal's "entraves" dataset.
 *
 * <p>{@code workId} is the id of the road work that caused the obstruction, so
 * it joins back to {@link WorkRecord#id()}.
 */
public record ObstructionRecord(String workId, String street, String impact) {

    public boolean belongsToWork(String id) {
        return workId.equalsIgnoreCase(id);
    }

    public boolean isOnStreet(String streetName) {
        return street.equalsIgnoreCase(streetName);
    }
}
