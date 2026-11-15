package _5bits.organik.platform.products.interfaces.rest.transform;

import _5bits.organik.platform.products.domain.model.aggregates.Category;
import _5bits.organik.platform.products.interfaces.rest.resources.CategoryResource;


/**
 * Assembler that converts a {@link Category} aggregate into a {@link CategoryResource}.
 */
public class CategoryResourceFromEntityAssembler {
    public static CategoryResource toResourceFromEntity(Category entity) {
        return new CategoryResource(
                entity.getId(),
                entity.getName().value(),
                entity.getTone().name());
    }
}
