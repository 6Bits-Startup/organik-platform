package _5bits.organik.platform.inventory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * Request resource used to register an inventory movement.
 */
@Schema(name = "RegisterInventoryMovementRequest", description = "Request payload for registering an inventory movement")
public record RegisterInventoryMovementResource(
    @Schema(description = "Stock lot affected by the movement", example = "1")
    @NotNull(message = "{inventory.error.stockLotId.notNull}")
    @Positive(message = "{inventory.error.stockLotId.min}")
    Long stockLotId,

    @Schema(description = "Movement type", example = "EXIT", allowableValues = {"ENTRY", "EXIT", "ADJUSTMENT", "WASTE"})
    @NotBlank(message = "{inventory.error.movementType.notBlank}")
    @Pattern(regexp = "^(ENTRY|EXIT|ADJUSTMENT|WASTE)$", message = "{inventory.error.movementType.pattern}")
    String type,

    @Schema(description = "Quantity of the movement", example = "12", minimum = "1")
    @NotNull(message = "{inventory.error.quantity.notNull}")
    @Positive(message = "{inventory.error.quantity.positive}")
    Integer quantity,

    @Schema(description = "Reason, required for adjustments and waste", example = "Damaged packaging", maxLength = 256)
    @Size(max = 256, message = "{inventory.error.movementReason.size}")
    String reason
) {
    @AssertTrue(message = "{inventory.error.movementReason.required}")
    @Schema(hidden = true)
    public boolean isReasonProvidedWhenRequired() {
        boolean requiresReason = "ADJUSTMENT".equals(type) || "WASTE".equals(type);
        return !requiresReason || (reason != null && !reason.isBlank());
    }
}
