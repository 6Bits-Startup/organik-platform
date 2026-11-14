package _5bits.organik.platform.profiles.domain.model.valueobjects;

import java.util.regex.Pattern;

/**
 * Email address of a profile.
 *
 * @param value email
 */
public record EmailAddress(String value) {
    private static final int MAX_LENGTH = 256;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$");

    public EmailAddress {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("email must not be null or blank.");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("email must not exceed 256 characters.");
        }
        if (!ALLOWED_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("email is not valid.");
        }
    }
}
