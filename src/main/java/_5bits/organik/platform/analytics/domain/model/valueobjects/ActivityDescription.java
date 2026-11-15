package _5bits.organik.platform.analytics.domain.model.valueobjects;

/**
 * Human readable description of an activity.
 *
 * @param value description
 */
public record ActivityDescription(String value) {
    private static final int MAX_LENGTH = 512;

    public ActivityDescription {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("description must not be null or blank.");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("description must not exceed 512 characters.");
        }
    }
}
