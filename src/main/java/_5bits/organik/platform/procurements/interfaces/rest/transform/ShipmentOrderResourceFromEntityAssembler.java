package _5bits.organik.platform.procurements.interfaces.rest.transform;

import _5bits.organik.platform.procurements.domain.model.aggregates.ShipmentOrder;
import _5bits.organik.platform.procurements.interfaces.rest.resources.ShipmentOrderResource;


/**
 * Assembler that converts a {@link ShipmentOrder} aggregate into a {@link ShipmentOrderResource}.
 */
public class ShipmentOrderResourceFromEntityAssembler {
    public static ShipmentOrderResource toResourceFromEntity(ShipmentOrder entity) {
        return new ShipmentOrderResource(
                entity.getId(),
                entity.getSupplierId().value(),
                entity.getMinimarketId().value(),
                entity.getLines().stream().map(ShipmentOrderLineResourceFromEntityAssembler::toResourceFromEntity).toList(),
                entity.getStatus().name(),
                entity.getDecidedBy() == null ? null : entity.getDecidedBy().value(),
                entity.getDecidedAt(),
                entity.getRejectionReason() == null ? null : entity.getRejectionReason().value(),
                entity.getCreatedAt());
    }
}
