package _5bits.organik.platform.products.interfaces.rest.transform;

import _5bits.organik.platform.products.domain.model.commands.UpdateProductCommand;
import _5bits.organik.platform.products.interfaces.rest.resources.UpdateProductResource;
import _5bits.organik.platform.products.domain.model.valueobjects.*;

/**
 * Assembler that converts a {@link UpdateProductResource} into a {@link UpdateProductCommand}.
 */
public class UpdateProductCommandFromResourceAssembler {
    public static UpdateProductCommand toCommandFromResource(Long productId, UpdateProductResource resource) {
        return new UpdateProductCommand(
                productId,
                new ProductName(resource.name()),
                new CategoryId(resource.categoryId()),
                resource.supplierId() == null || resource.supplierId().isBlank() ? null : new SupplierId(resource.supplierId()),
                MeasurementUnit.valueOf(resource.unit()),
                new MinimumStock(resource.minimumStock()));
    }
}
