package _5bits.organik.platform.products.interfaces.rest.transform;

import _5bits.organik.platform.products.domain.model.commands.CreateCategoryCommand;
import _5bits.organik.platform.products.interfaces.rest.resources.CreateCategoryResource;
import _5bits.organik.platform.products.domain.model.valueobjects.CategoryName;
import _5bits.organik.platform.products.domain.model.valueobjects.CategoryTone;

/**
 * Assembler that converts a {@link CreateCategoryResource} into a {@link CreateCategoryCommand}.
 */
public class CreateCategoryCommandFromResourceAssembler {
    public static CreateCategoryCommand toCommandFromResource(CreateCategoryResource resource) {
        return new CreateCategoryCommand(
                new CategoryName(resource.name()),
                CategoryTone.valueOf(resource.tone()));
    }
}
