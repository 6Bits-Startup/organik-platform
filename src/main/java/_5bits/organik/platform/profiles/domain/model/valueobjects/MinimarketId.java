package _5bits.organik.platform.profiles.domain.model.valueobjects;

import java.util.regex.Pattern;

/**
 * Minimarket where the profile works.
 *
 * @param value minimarketId
 */
public record MinimarketId(String value) {
    private static final int MAX_LENGTH = 256;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");

    public MinimarketId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("minimarketId must not be null or blank.");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("minimarketId must not exceed 256 characters.");
        }
        if (!ALLOWED_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("minimarketId is not valid.");
        }
    }
}
