package _5bits.organik.platform.suppliers.domain.model.valueobjects;

import java.util.regex.Pattern;

public record PhoneNumber(String value) {
    private static final int MAX_LENGTH = 20;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^\\+?[0-9][0-9 -]{5,19}$");
    private static final String NOT_BLANK_MESSAGE = "phoneNumber must not be null or blank.";
    private static final String SIZE_MESSAGE = "phoneNumber must not exceed 20 characters.";
    private static final String PATTERN_MESSAGE = "phoneNumber may only contain digits, spaces, '-' and a leading '+'.";

    public PhoneNumber {
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
