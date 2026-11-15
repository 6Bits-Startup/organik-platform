package _5bits.organik.platform.suppliers.interfaces.rest.transform;

import _5bits.organik.platform.suppliers.domain.model.commands.UpdateOfferedProductCommand;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.suppliers.domain.model.valueobjects.Quantity;
import _5bits.organik.platform.suppliers.interfaces.rest.resources.UpdateOfferedProductResource;

/**
 * Assembler that converts an {@link UpdateOfferedProductResource} into an {@link UpdateOfferedProductCommand}.
 */
public class UpdateOfferedProductCommandFromResourceAssembler {
    public static UpdateOfferedProductCommand toCommandFromResource(Long offeredProductId, UpdateOfferedProductResource resource) {
        return new UpdateOfferedProductCommand(
                offeredProductId,
                new LotCode(resource.lotCode()),
                new Quantity(resource.availableQuantity()),
                resource.expiresOn());
    }
}
