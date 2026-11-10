package _5bits.organik.platform.communication.domain.model.valueobjects;

public record NotificationTitle(String value) {
    private static final int MAX_LENGTH = 150;
    private static final String NOT_BLANK_MESSAGE_KEY = "notification.error.title.notBlank";
    private static final String SIZE_MESSAGE_KEY = "notification.error.title.size";

    public NotificationTitle {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }
        value = value.trim();
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE_KEY);
        }
    }
}