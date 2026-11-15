package _5bits.organik.platform.suppliers.interfaces.rest.transform;

import _5bits.organik.platform.suppliers.domain.model.commands.CreateOfferedProductCommand;
import _5bits.organik.platform.suppliers.interfaces.rest.resources.CreateOfferedProductResource;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.ProductId;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.SupplierId;

/**
 * Assembler that converts a {@link CreateOfferedProductResource} into a {@link CreateOfferedProductCommand}.
 */
public class CreateOfferedProductCommandFromResourceAssembler {
    public static CreateOfferedProductCommand toCommandFromResource(CreateOfferedProductResource resource) {
        return new CreateOfferedProductCommand(
                new SupplierId(resource.supplierId()),
                new ProductId(resource.productId()),
                new LotCode(resource.lotCode()),
                new Quantity(resource.availableQuantity()),
                resource.expiresOn());
    }
}
