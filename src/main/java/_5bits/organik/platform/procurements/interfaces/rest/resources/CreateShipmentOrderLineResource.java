package _5bits.organik.platform.procurements.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * Request resource for a line of a new shipment order.
 */
@Schema(name = "CreateShipmentOrderLineRequest", description = "Line of a shipment order")
public record CreateShipmentOrderLineResource(
    @Schema(description = "Product identifier", example = "ORG-01")
    @NotBlank(message = "{shipmentOrder.error.productId.notBlank}")
    @Size(max = 256, message = "{shipmentOrder.error.productId.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{shipmentOrder.error.productId.pattern}")
    String productId,

    @Schema(description = "Quantity sent", example = "30", minimum = "1")
    @NotNull(message = "{shipmentOrder.error.quantity.notNull}")
    @Min(value = 1, message = "{shipmentOrder.error.quantity.min}")
    Integer quantity,

    @Schema(description = "Lot code", example = "LT-512", maxLength = 32)
    @NotBlank(message = "{shipmentOrder.error.lotCode.notBlank}")
    @Size(max = 32, message = "{shipmentOrder.error.lotCode.size}")
    @Pattern(regexp = "^[a-zA-Z0-9-]+$", message = "{shipmentOrder.error.lotCode.pattern}")
    String lotCode,

    @Schema(description = "Expiration date of the lot", example = "2026-12-20")
    @NotNull(message = "{shipmentOrder.error.expiresOn.notNull}")
    LocalDate expiresOn
) {
}
