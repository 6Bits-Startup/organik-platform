package _5bits.organik.platform.communication.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request resource used to create a notification.
 */
@Schema(
    name = "CreateNotificationRequest",
    description = "Request payload for creating a notification",
    example = "{\"minimarketId\": \"mm-vida-verde\", \"type\": \"LOW_STOCK\", \"severity\": \"WARNING\", \"title\": \"Low stock in lot LT-204\"}"
)
public record CreateNotificationResource(
    @Schema(description = "Minimarket whose staff receives the notification", example = "mm-vida-verde")
    @NotBlank(message = "{notification.error.minimarketId.notBlank}")
    @Size(max = 256, message = "{notification.error.minimarketId.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{notification.error.minimarketId.pattern}")
    String minimarketId,
    @Schema(description = "Notification type", example = "LOW_STOCK",
            allowableValues = {"EXPIRATION", "CONSERVATION", "LOW_STOCK", "REPLENISHMENT", "ORDER"})
    @NotBlank(message = "{notification.error.type.notBlank}")
    @Pattern(regexp = "^(EXPIRATION|CONSERVATION|LOW_STOCK|REPLENISHMENT|ORDER)$", message = "{notification.error.type.pattern}")
    String type,

    @Schema(description = "Notification severity", example = "WARNING", allowableValues = {"INFO", "WARNING", "CRITICAL"})
    @NotBlank(message = "{notification.error.severity.notBlank}")
    @Pattern(regexp = "^(INFO|WARNING|CRITICAL)$", message = "{notification.error.severity.pattern}")
    String severity,

    @Schema(description = "Notification title", example = "Low stock in lot LT-204", maxLength = 150)
    @NotBlank(message = "{notification.error.title.notBlank}")
    @Size(max = 150, message = "{notification.error.title.size}")
    String title
) {
}
