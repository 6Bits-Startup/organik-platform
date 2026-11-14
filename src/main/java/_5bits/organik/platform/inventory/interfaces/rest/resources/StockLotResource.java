package _5bits.organik.platform.inventory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

/**
 * Response resource representing a stock lot.
 */
@Schema(name = "StockLotResponse", description = "Stock lot information response")
public record StockLotResource(
    @Schema(description = "Stock lot unique identifier", example = "1")
    Long id,
    @Schema(description = "Minimarket that owns the lot", example = "mm-vida-verde")
    String minimarketId,
    @Schema(description = "Product of the lot", example = "ORG-01")
    String productId,
    @Schema(description = "Lot code", example = "LT-204")
    String lotCode,
    @Schema(description = "Current quantity", example = "248")
    Integer quantity,
    @Schema(description = "Minimum stock", example = "20")
    Integer minimumStock,
    @Schema(description = "Expiration date", example = "2026-12-20")
    LocalDate expirationDate,
    @Schema(description = "Whether the lot is at or below its minimum stock", example = "false")
    boolean lowStock
) {
}
