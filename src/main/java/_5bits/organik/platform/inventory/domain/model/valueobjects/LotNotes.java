package _5bits.organik.platform.inventory.domain.model.valueobjects;

/**
 * Free notes about a stock lot; they may be empty.
 *
 * @param value notes of the lot
 */
public record LotNotes(String value) {
    private static final int MAX_LENGTH = 500;
    private static final String NOT_NULL_MESSAGE = "notes must not be null.";
    private static final String SIZE_MESSAGE = "notes must not exceed 500 characters.";

    public LotNotes {
        if (value == null) {
            throw new IllegalArgumentException(NOT_NULL_MESSAGE);
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE);
        }
    }
}
