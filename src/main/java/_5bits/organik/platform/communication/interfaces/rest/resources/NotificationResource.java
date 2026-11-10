package _5bits.organik.platform.communication.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Response resource representing a notification")
public record NotificationResource(
        @Schema(description = "Persistent identifier", example = "1")
        Long id,
        @Schema(description = "Type of the notification", example = "LOW_STOCK")
        String type,
        @Schema(description = "Severity level of the notification", example = "WARNING")
        String severity,
        @Schema(description = "Title of the notification", example = "Stock bajo en quinua blanca")
        String title,
        @Schema(description = "Whether the notification has been read", example = "false")
        boolean isRead,
        @Schema(description = "Creation timestamp")
        Instant createdAt,
        @Schema(description = "Last update timestamp")
        Instant updatedAt) {
}