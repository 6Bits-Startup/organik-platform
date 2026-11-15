package _5bits.organik.platform.suppliers.domain.model.valueobjects;

import java.util.regex.Pattern;

public record EmailAddress(String value) {
    private static final int MAX_LENGTH = 256;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]{2,}$");
    private static final String NOT_BLANK_MESSAGE = "email must not be null or blank.";
    private static final String SIZE_MESSAGE = "email must not exceed 256 characters.";
    private static final String PATTERN_MESSAGE = "email is not a valid email address.";

    public EmailAddress {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE);
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE);
        }
        if (!ALLOWED_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException(PATTERN_MESSAGE);
        }
    }

}
