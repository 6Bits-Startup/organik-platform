package _5bits.organik.platform.procurements.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request resource used to reject a shipment order.
 */
@Schema(name = "RejectShipmentOrderRequest", description = "Request payload for rejecting a shipment order")
public record RejectShipmentOrderResource(
    @Schema(description = "User that rejects the order", example = "1")
    @NotNull(message = "{shipmentOrder.error.decidedBy.notNull}")
    @Min(value = 1, message = "{shipmentOrder.error.decidedBy.min}")
    Long decidedBy,

    @Schema(description = "Rejection reason", example = "Products arrived damaged", maxLength = 512)
    @NotBlank(message = "{shipmentOrder.error.rejectionReason.notBlank}")
    @Size(max = 512, message = "{shipmentOrder.error.rejectionReason.size}")
    String reason
) {
}
