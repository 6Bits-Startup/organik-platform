package _5bits.organik.platform.inventory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * Response resource representing an inventory movement.
 */
@Schema(name = "InventoryMovementResponse", description = "Inventory movement information response")
public record InventoryMovementResource(
    @Schema(description = "Inventory movement unique identifier", example = "1")
    Long id,
    @Schema(description = "Stock lot affected by the movement", example = "1")
    Long stockLotId,
    @Schema(description = "Movement type", example = "EXIT")
    String type,
    @Schema(description = "Quantity of the movement", example = "12")
    Integer quantity,
    @Schema(description = "Reason of the movement", example = "Damaged packaging")
    String reason,
    @Schema(description = "Registration date")
    Date registeredAt
) {
}
