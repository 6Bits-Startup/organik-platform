package _5bits.organik.platform.products.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * Request resource used to update a product.
 */
@Schema(name = "UpdateProductRequest", description = "Request payload for updating a product")
public record UpdateProductResource(
    @Schema(description = "Product name", example = "Organic yogurt", maxLength = 256)
    @NotBlank(message = "{product.error.name.notBlank}")
    @Size(max = 256, message = "{product.error.name.size}")
    String name,

    @Schema(description = "Category of the product", example = "1")
    @NotNull(message = "{product.error.categoryId.notNull}")
    @Positive(message = "{product.error.categoryId.invalid}")
    Long categoryId,

    @Schema(description = "Usual supplier of the product", example = "sup-bioandes")
    @Size(max = 256, message = "{product.error.supplierId.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "{product.error.supplierId.pattern}")
    String supplierId,

    @Schema(description = "Measurement unit", example = "UNIT", allowableValues = {"UNIT", "KG", "LITER", "PACK"})
    @NotBlank(message = "{product.error.unit.notBlank}")
    @Pattern(regexp = "^(UNIT|KG|LITER|PACK)$", message = "{product.error.unit.pattern}")
    String unit,

    @Schema(description = "Minimum stock", example = "20", minimum = "0")
    @NotNull(message = "{product.error.minimumStock.notNull}")
    @Min(value = 0, message = "{product.error.minimumStock.min}")
    Integer minimumStock
) {
}
