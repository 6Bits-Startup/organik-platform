package _5bits.organik.platform.communication.domain.model.valueobjects;

import java.util.Set;

public record NotificationType(String value) {
    private static final Set<String> ALLOWED_TYPES = Set.of(
            "EXPIRATION",
            "CONSERVATION",
            "LOW_STOCK",
            "REPLENISHMENT",
            "ORDER"
    );
    private static final String NOT_BLANK_MESSAGE = "The notification type is required.";
    private static final String PATTERN_MESSAGE = "The notification type must be EXPIRATION, CONSERVATION, LOW_STOCK, REPLENISHMENT or ORDER.";

    public NotificationType {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE);
        }
        value = value.trim().toUpperCase();
        if (!ALLOWED_TYPES.contains(value)) {
            throw new IllegalArgumentException(PATTERN_MESSAGE);
        }
    }
}