package _5bits.organik.platform.inventory.domain.model.valueobjects;

/**
 * Place of the minimarket where a stock lot is kept, e.g. "Cold room 1".
 *
 * @param value location of the lot
 */
public record LotLocation(String value) {
    private static final int MAX_LENGTH = 100;
    private static final String NOT_BLANK_MESSAGE = "location must not be null or blank.";
    private static final String SIZE_MESSAGE = "location must not exceed 100 characters.";

    public LotLocation {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE);
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE);
        }
    }
}
