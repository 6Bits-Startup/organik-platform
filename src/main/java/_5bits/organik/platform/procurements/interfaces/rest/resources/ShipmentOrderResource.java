package _5bits.organik.platform.procurements.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;

/**
 * Response resource representing a shipment order.
 */
@Schema(name = "ShipmentOrderResponse", description = "Shipment order information response")
public record ShipmentOrderResource(
    @Schema(description = "Shipment order unique identifier", example = "1")
    Long id,
    @Schema(description = "Supplier that sends the order", example = "sup-bioandes")
    String supplierId,
    @Schema(description = "Minimarket that receives the order", example = "mm-vida-verde")
    String minimarketId,
    @Schema(description = "Lines of the order")
    List<ShipmentOrderLineResource> lines,
    @Schema(description = "Order status", example = "PENDING", allowableValues = {"PENDING", "ACCEPTED", "REJECTED"})
    String status,
    @Schema(description = "User that decided the order", example = "1")
    Long decidedBy,
    @Schema(description = "Decision date")
    Date decidedAt,
    @Schema(description = "Rejection reason", example = "Products arrived damaged")
    String rejectionReason,
    @Schema(description = "Creation date")
    Date createdAt
) {
}
