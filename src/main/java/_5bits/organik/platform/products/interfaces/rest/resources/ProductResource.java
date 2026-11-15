package _5bits.organik.platform.products.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response resource representing a product.
 */
@Schema(name = "ProductResponse", description = "Product information response")
public record ProductResource(
    @Schema(description = "Product unique identifier", example = "1")
    Long id,
    @Schema(description = "Stock keeping unit", example = "ORG-01")
    String sku,
    @Schema(description = "Product name", example = "Organic yogurt")
    String name,
    @Schema(description = "Category of the product", example = "1")
    Long categoryId,
    @Schema(description = "Usual supplier of the product", example = "sup-bioandes")
    String supplierId,
    @Schema(description = "Measurement unit", example = "UNIT")
    String unit,
    @Schema(description = "Minimum stock", example = "20")
    Integer minimumStock
) {
}
