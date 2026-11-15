package _5bits.organik.platform.procurements.domain.model.valueobjects;

import java.util.regex.Pattern;

public record SupplierId(String value) {
    private static final int MAX_LENGTH = 256;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");
    private static final String NOT_BLANK_MESSAGE = "supplierId must not be null or blank.";
    private static final String SIZE_MESSAGE = "supplierId must not exceed 256 characters.";
    private static final String PATTERN_MESSAGE = "supplierId contains invalid characters.";

    public SupplierId {
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
