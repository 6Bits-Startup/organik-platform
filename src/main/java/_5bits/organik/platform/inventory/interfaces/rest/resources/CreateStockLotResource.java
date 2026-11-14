package _5bits.organik.platform.inventory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * Request resource used to register a stock lot.
 */
@Schema(name = "CreateStockLotRequest", description = "Request payload for registering a stock lot")
public record CreateStockLotResource(
    @Schema(description = "Minimarket that receives the lot", example = "mm-vida-verde")
    @NotBlank(message = "{inventory.error.minimarketId.notBlank}")
    @Size(max = 256, message = "{inventory.error.minimarketId.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{inventory.error.minimarketId.pattern}")
    String minimarketId,

    @Schema(description = "Product of the lot", example = "ORG-01")
    @NotBlank(message = "{inventory.error.productId.notBlank}")
    @Size(max = 256, message = "{inventory.error.productId.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{inventory.error.productId.pattern}")
    String productId,

    @Schema(description = "Lot code", example = "LT-204", maxLength = 32)
    @NotBlank(message = "{inventory.error.lotCode.notBlank}")
    @Size(max = 32, message = "{inventory.error.lotCode.size}")
    @Pattern(regexp = "^[a-zA-Z0-9-]+$", message = "{inventory.error.lotCode.pattern}")
    String lotCode,

    @Schema(description = "Received quantity", example = "248", minimum = "1")
    @NotNull(message = "{inventory.error.quantity.notNull}")
    @Positive(message = "{inventory.error.quantity.positive}")
    Integer quantity,

    @Schema(description = "Minimum stock of the lot", example = "20", minimum = "0")
    @NotNull(message = "{inventory.error.minimumStock.notNull}")
    @PositiveOrZero(message = "{inventory.error.minimumStock.min}")
    Integer minimumStock,

    @Schema(description = "Expiration date", example = "2026-12-20")
    @NotNull(message = "{inventory.error.expirationDate.notNull}")
    LocalDate expirationDate
) {
}
