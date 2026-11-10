package _5bits.organik.platform.communication.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Request resource used to create a notification")
public record CreateNotificationResource(
        @Schema(
                description = "Type of the notification",
                example = "LOW_STOCK",
                allowableValues = {"EXPIRATION", "CONSERVATION", "LOW_STOCK", "REPLENISHMENT", "ORDER"},
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{notification.error.type.notBlank}")
        @Pattern(regexp = "^(EXPIRATION|CONSERVATION|LOW_STOCK|REPLENISHMENT|ORDER)$", message = "{notification.error.type.pattern}")
        String type,

        @Schema(
                description = "Severity level of the notification",
                example = "WARNING",
                allowableValues = {"INFO", "WARNING", "CRITICAL"},
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{notification.error.severity.notBlank}")
        @Pattern(regexp = "^(INFO|WARNING|CRITICAL)$", message = "{notification.error.severity.pattern}")
        String severity,

        @Schema(
                description = "Title or summary message of the notification",
                example = "Stock bajo en quinua blanca",
                maxLength = 150,
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{notification.error.title.notBlank}")
        @Size(max = 150, message = "{notification.error.title.size}")
        String title) {
}