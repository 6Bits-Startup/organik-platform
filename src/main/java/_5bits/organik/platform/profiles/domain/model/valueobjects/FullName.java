package _5bits.organik.platform.profiles.domain.model.valueobjects;

/**
 * Full name of the person behind a profile.
 *
 * @param value fullName
 */
public record FullName(String value) {
    private static final int MAX_LENGTH = 256;

    public FullName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("fullName must not be null or blank.");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("fullName must not exceed 256 characters.");
        }
    }
}
