package _5bits.organik.platform.communication.domain.model.valueobjects;

import java.util.Set;

public record Severity(String value) {
    private static final Set<String> ALLOWED_SEVERITIES = Set.of(
            "INFO",
            "WARNING",
            "CRITICAL"
    );
    private static final String NOT_BLANK_MESSAGE_KEY = "notification.error.severity.notBlank";
    private static final String PATTERN_MESSAGE_KEY = "notification.error.severity.pattern";

    public Severity {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }
        value = value.trim().toUpperCase();
        if (!ALLOWED_SEVERITIES.contains(value)) {
            throw new IllegalArgumentException(PATTERN_MESSAGE_KEY);
        }
    }
}