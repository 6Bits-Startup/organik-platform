package _5bits.organik.platform.procurements.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

/**
 * Request resource used to create a shipment order.
 */
@Schema(name = "CreateShipmentOrderRequest", description = "Request payload for creating a shipment order")
public record CreateShipmentOrderResource(
    @Schema(description = "Supplier that sends the order", example = "sup-bioandes")
    @NotBlank(message = "{shipmentOrder.error.supplierId.notBlank}")
    @Size(max = 256, message = "{shipmentOrder.error.supplierId.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{shipmentOrder.error.supplierId.pattern}")
    String supplierId,

    @Schema(description = "Minimarket that receives the order", example = "mm-vida-verde")
    @NotBlank(message = "{shipmentOrder.error.minimarketId.notBlank}")
    @Size(max = 256, message = "{shipmentOrder.error.minimarketId.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{shipmentOrder.error.minimarketId.pattern}")
    String minimarketId,

    @Schema(description = "Lines of the order")
    @NotEmpty(message = "{shipmentOrder.error.lines.notEmpty}")
    List<@NotNull @Valid CreateShipmentOrderLineResource> lines
) {
}
