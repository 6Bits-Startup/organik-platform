package _5bits.organik.platform.profiles.domain.model.valueobjects;

import java.util.regex.Pattern;

/**
 * Supplier the profile belongs to.
 *
 * @param value supplierId
 */
public record SupplierId(String value) {
    private static final int MAX_LENGTH = 256;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");

    public SupplierId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("supplierId must not be null or blank.");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("supplierId must not exceed 256 characters.");
        }
        if (!ALLOWED_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("supplierId is not valid.");
        }
    }
}
