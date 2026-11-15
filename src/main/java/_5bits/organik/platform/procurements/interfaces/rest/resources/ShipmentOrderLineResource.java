package _5bits.organik.platform.procurements.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

/**
 * Response resource representing a line of a shipment order.
 */
@Schema(name = "ShipmentOrderLineResponse", description = "Shipment order line information")
public record ShipmentOrderLineResource(
    @Schema(description = "Line unique identifier", example = "1")
    Long id,
    @Schema(description = "Product identifier", example = "ORG-01")
    String productId,
    @Schema(description = "Quantity sent", example = "30")
    Integer quantity,
    @Schema(description = "Lot code", example = "LT-512")
    String lotCode,
    @Schema(description = "Expiration date of the lot", example = "2026-12-20")
    LocalDate expiresOn
) {
}
