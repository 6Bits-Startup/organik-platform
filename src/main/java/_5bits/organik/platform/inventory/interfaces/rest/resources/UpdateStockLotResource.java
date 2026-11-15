package _5bits.organik.platform.inventory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Request resource used to update the information of a stock lot that does not change its stock.
 */
@Schema(name = "UpdateStockLotRequest", description = "Request payload for updating the location, notes and expiration date of a stock lot")
public record UpdateStockLotResource(
    @Schema(description = "Place of the minimarket where the lot is kept", example = "Cold room 1", maxLength = 100)
    @NotBlank(message = "{inventory.error.location.notBlank}")
    @Size(max = 100, message = "{inventory.error.location.size}")
    String location,

    @Schema(description = "Notes about the lot", example = "Moved to the front shelf", maxLength = 500)
    @Size(max = 500, message = "{inventory.error.notes.size}")
    String notes,

    @Schema(description = "Expiration date", example = "2026-12-20")
    @NotNull(message = "{inventory.error.expirationDate.notNull}")
    LocalDate expirationDate
) {
}
