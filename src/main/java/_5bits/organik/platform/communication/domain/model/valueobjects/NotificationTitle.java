package _5bits.organik.platform.communication.domain.model.valueobjects;

public record NotificationTitle(String value) {
    private static final int MAX_LENGTH = 150;
    private static final String NOT_BLANK_MESSAGE = "The notification title is required.";
    private static final String SIZE_MESSAGE = "The notification title must not exceed 150 characters.";

    public NotificationTitle {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE);
        }
        value = value.trim();
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE);
        }
    }
}