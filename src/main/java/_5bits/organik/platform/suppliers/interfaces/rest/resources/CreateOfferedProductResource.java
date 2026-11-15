package _5bits.organik.platform.suppliers.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * Request resource used to publish an offered product in a supplier catalog.
 */
@Schema(name = "CreateOfferedProductRequest", description = "Request payload for publishing an offered product")
public record CreateOfferedProductResource(
    @Schema(description = "Supplier that offers the product", example = "sup-bioandes")
    @NotBlank(message = "{offeredProduct.error.supplierId.notBlank}")
    @Size(max = 256, message = "{offeredProduct.error.supplierId.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{offeredProduct.error.supplierId.pattern}")
    String supplierId,

    @Schema(description = "Offered product", example = "ORG-01")
    @NotBlank(message = "{offeredProduct.error.productId.notBlank}")
    @Size(max = 256, message = "{offeredProduct.error.productId.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{offeredProduct.error.productId.pattern}")
    String productId,

    @Schema(description = "Lot code", example = "LT-512", maxLength = 32)
    @NotBlank(message = "{offeredProduct.error.lotCode.notBlank}")
    @Size(max = 32, message = "{offeredProduct.error.lotCode.size}")
    @Pattern(regexp = "^[a-zA-Z0-9-]+$", message = "{offeredProduct.error.lotCode.pattern}")
    String lotCode,

    @Schema(description = "Available quantity", example = "120", minimum = "0")
    @NotNull(message = "{offeredProduct.error.quantity.notNull}")
    @Min(value = 0, message = "{offeredProduct.error.quantity.min}")
    Integer availableQuantity,

    @Schema(description = "Expiration date of the lot", example = "2026-12-20")
    @NotNull(message = "{offeredProduct.error.expiresOn.notNull}")
    LocalDate expiresOn
) {
}
