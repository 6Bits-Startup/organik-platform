package _5bits.organik.platform.products.domain.model.valueobjects;

import java.util.regex.Pattern;

public record Sku(String value) {
    private static final int MAX_LENGTH = 32;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[A-Z0-9-]+$");
    private static final String NOT_BLANK_MESSAGE = "sku must not be null or blank.";
    private static final String SIZE_MESSAGE = "sku must not exceed 32 characters.";
    private static final String PATTERN_MESSAGE = "sku contains invalid characters. Only uppercase letters, digits and '-' are allowed.";

    public Sku {
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
