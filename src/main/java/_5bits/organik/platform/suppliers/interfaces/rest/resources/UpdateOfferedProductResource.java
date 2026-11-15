package _5bits.organik.platform.suppliers.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * Request resource used to update a product offered in a supplier catalog.
 */
@Schema(name = "UpdateOfferedProductRequest", description = "Request payload for updating an offered product")
public record UpdateOfferedProductResource(
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
