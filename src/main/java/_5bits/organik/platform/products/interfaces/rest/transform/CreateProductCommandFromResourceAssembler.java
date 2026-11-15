package _5bits.organik.platform.products.interfaces.rest.transform;

import _5bits.organik.platform.products.domain.model.commands.CreateProductCommand;
import _5bits.organik.platform.products.interfaces.rest.resources.CreateProductResource;
import _5bits.organik.platform.products.domain.model.valueobjects.*;

/**
 * Assembler that converts a {@link CreateProductResource} into a {@link CreateProductCommand}.
 */
public class CreateProductCommandFromResourceAssembler {
    public static CreateProductCommand toCommandFromResource(CreateProductResource resource) {
        return new CreateProductCommand(
                new Sku(resource.sku()),
                new ProductName(resource.name()),
                new CategoryId(resource.categoryId()),
                resource.supplierId() == null || resource.supplierId().isBlank() ? null : new SupplierId(resource.supplierId()),
                MeasurementUnit.valueOf(resource.unit()),
                new MinimumStock(resource.minimumStock()));
    }
}
