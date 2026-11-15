package _5bits.organik.platform.conservation.domain.model.valueobjects;

import java.util.regex.Pattern;

public record MinimarketId(String value) {
    private static final int MAX_LENGTH = 256;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");
    private static final String NOT_BLANK_MESSAGE = "minimarketId must not be null or blank.";
    private static final String SIZE_MESSAGE = "minimarketId must not exceed 256 characters.";
    private static final String PATTERN_MESSAGE = "minimarketId contains invalid characters. Only letters, digits, underscores and hyphens are allowed.";

    public MinimarketId {
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
