package _5bits.organik.platform.procurements.interfaces.rest.transform;

import _5bits.organik.platform.procurements.domain.model.entities.ShipmentOrderLine;
import _5bits.organik.platform.procurements.interfaces.rest.resources.ShipmentOrderLineResource;

/**
 * Assembler that converts a {@link ShipmentOrderLine} entity into a {@link ShipmentOrderLineResource}.
 */
public class ShipmentOrderLineResourceFromEntityAssembler {
    public static ShipmentOrderLineResource toResourceFromEntity(ShipmentOrderLine entity) {
        return new ShipmentOrderLineResource(
                entity.getId(),
                entity.getProductId().value(),
                entity.getQuantity().value(),
                entity.getLotCode().value(),
                entity.getExpiresOn());
    }
}
