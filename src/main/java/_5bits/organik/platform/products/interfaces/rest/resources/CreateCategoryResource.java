package _5bits.organik.platform.products.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request resource used to create a category.
 */
@Schema(name = "CreateCategoryRequest", description = "Request payload for creating a category")
public record CreateCategoryResource(
    @Schema(description = "Category name", example = "Dairy", maxLength = 128)
    @NotBlank(message = "{category.error.name.notBlank}")
    @Size(max = 128, message = "{category.error.name.size}")
    String name,

    @Schema(description = "Color tone of the category", example = "GREEN", allowableValues = {"GREEN", "BLUE", "ORANGE", "YELLOW", "NEUTRAL"})
    @NotBlank(message = "{category.error.tone.notBlank}")
    @Pattern(regexp = "^(GREEN|BLUE|ORANGE|YELLOW|NEUTRAL)$", message = "{category.error.tone.pattern}")
    String tone
) {
}
