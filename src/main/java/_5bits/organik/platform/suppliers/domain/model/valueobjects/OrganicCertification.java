package _5bits.organik.platform.suppliers.domain.model.valueobjects;

import java.util.regex.Pattern;

public record OrganicCertification(String value) {
    private static final int MAX_LENGTH = 64;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[a-zA-Z0-9 .-]+$");
    private static final String NOT_BLANK_MESSAGE = "organicCertification must not be null or blank.";
    private static final String SIZE_MESSAGE = "organicCertification must not exceed 64 characters.";
    private static final String PATTERN_MESSAGE = "organicCertification contains invalid characters.";

    public OrganicCertification {
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
