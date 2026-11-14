package _5bits.organik.platform.suppliers.domain.model.valueobjects;

public record BusinessName(String value) {
    private static final int MAX_LENGTH = 256;
    private static final String NOT_BLANK_MESSAGE = "businessName must not be null or blank.";
    private static final String SIZE_MESSAGE = "businessName must not exceed 256 characters.";

    public BusinessName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE);
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE);
        }
    }

}
