package _5bits.organik.platform.suppliers.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

/**
 * Response resource representing an offered product.
 */
@Schema(name = "OfferedProductResponse", description = "Offered product information response")
public record OfferedProductResource(
    @Schema(description = "Offered product unique identifier", example = "1")
    Long id,
    @Schema(description = "Supplier that offers the product", example = "sup-bioandes")
    String supplierId,
    @Schema(description = "Offered product", example = "ORG-01")
    String productId,
    @Schema(description = "Lot code", example = "LT-512")
    String lotCode,
    @Schema(description = "Available quantity", example = "120")
    Integer availableQuantity,
    @Schema(description = "Expiration date of the lot", example = "2026-12-20")
    LocalDate expiresOn
) {
}
