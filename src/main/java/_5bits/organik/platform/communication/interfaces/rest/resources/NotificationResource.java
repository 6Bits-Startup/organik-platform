package _5bits.organik.platform.communication.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * Response resource representing a notification.
 */
@Schema(name = "NotificationResponse", description = "Notification information response")
public record NotificationResource(
    @Schema(description = "Notification unique identifier", example = "1")
    Long id,
    @Schema(description = "Minimarket whose staff receives the notification", example = "mm-vida-verde")
    String minimarketId,
    @Schema(description = "Notification type", example = "LOW_STOCK")
    String type,
    @Schema(description = "Notification severity", example = "WARNING")
    String severity,
    @Schema(description = "Notification title", example = "Low stock in lot LT-204")
    String title,
    @Schema(description = "Whether the notification was read", example = "false")
    boolean isRead,
    @Schema(description = "Creation date")
    Date createdAt
) {
}
