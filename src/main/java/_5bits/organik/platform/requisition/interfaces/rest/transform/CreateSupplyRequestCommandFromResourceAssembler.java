package _5bits.organik.platform.requisition.interfaces.rest.transform;

import _5bits.organik.platform.requisition.domain.model.commands.CreateSupplyRequestCommand;
import _5bits.organik.platform.requisition.interfaces.rest.resources.CreateSupplyRequestResource;
import _5bits.organik.platform.requisition.domain.model.valueobjects.*;

/**
 * Assembler that converts a {@link CreateSupplyRequestResource} into a {@link CreateSupplyRequestCommand}.
 */
public class CreateSupplyRequestCommandFromResourceAssembler {
    public static CreateSupplyRequestCommand toCommandFromResource(CreateSupplyRequestResource resource) {
        return new CreateSupplyRequestCommand(
                new ProductId(resource.productId()),
                new SupplierId(resource.supplierId()),
                new MinimarketId(resource.minimarketId()),
                new Quantity(resource.quantity()),
                new RequestReason(resource.reason()),
                resource.requiredDate(),
                RequestPriority.valueOf(resource.priority()));
    }
}
