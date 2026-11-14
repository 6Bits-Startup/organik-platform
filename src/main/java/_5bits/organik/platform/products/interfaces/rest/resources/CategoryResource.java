package _5bits.organik.platform.products.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response resource representing a category.
 */
@Schema(name = "CategoryResponse", description = "Category information response")
public record CategoryResource(
    @Schema(description = "Category unique identifier", example = "1")
    Long id,
    @Schema(description = "Category name", example = "Dairy")
    String name,
    @Schema(description = "Color tone of the category", example = "GREEN")
    String tone
) {
}
