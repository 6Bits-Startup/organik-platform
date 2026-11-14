package _5bits.organik.platform.requisition.domain.model.valueobjects;

public record RequestReason(String value) {
    private static final int MAX_LENGTH = 512;
    private static final String NOT_BLANK_MESSAGE = "reason must not be null or blank.";
    private static final String SIZE_MESSAGE = "reason must not exceed 512 characters.";

    public RequestReason {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE);
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE);
        }
    }

}
