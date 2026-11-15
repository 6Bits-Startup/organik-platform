package _5bits.organik.platform.products.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * Request resource used to create a product.
 */
@Schema(name = "CreateProductRequest", description = "Request payload for creating a product")
public record CreateProductResource(
    @Schema(description = "Stock keeping unit", example = "ORG-01", maxLength = 32)
    @NotBlank(message = "{product.error.sku.notBlank}")
    @Size(max = 32, message = "{product.error.sku.size}")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "{product.error.sku.pattern}")
    String sku,

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
