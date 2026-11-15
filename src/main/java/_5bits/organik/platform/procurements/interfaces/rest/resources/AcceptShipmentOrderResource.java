package _5bits.organik.platform.procurements.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Request resource used to accept a shipment order.
 */
@Schema(name = "AcceptShipmentOrderRequest", description = "Request payload for accepting a shipment order")
public record AcceptShipmentOrderResource(
    @Schema(description = "User that accepts the order", example = "1")
    @NotNull(message = "{shipmentOrder.error.decidedBy.notNull}")
    @Min(value = 1, message = "{shipmentOrder.error.decidedBy.min}")
    Long decidedBy
) {
}
