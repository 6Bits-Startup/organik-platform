package _5bits.organik.platform.inventory.domain.model.valueobjects;

public record MovementReason(String value) {
    private static final int MAX_LENGTH = 256;
    private static final String NOT_BLANK_MESSAGE = "reason must not be blank.";
    private static final String SIZE_MESSAGE = "reason must not exceed 256 characters.";

    public MovementReason {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE);
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE);
        }
    }

}
