package _5bits.organik.platform.procurements.infrastructure.persistence.jpa.assemblers;

import _5bits.organik.platform.procurements.domain.model.aggregates.ShipmentOrder;
import _5bits.organik.platform.procurements.domain.model.entities.ShipmentOrderLine;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.entities.ShipmentOrderLinePersistenceEntity;
import _5bits.organik.platform.procurements.infrastructure.persistence.jpa.entities.ShipmentOrderPersistenceEntity;

import java.util.ArrayList;

/**
 * Assembler that maps between the {@link ShipmentOrder} domain aggregate and its JPA persistence entities.
 * <p>
 * Acts as the anti-corruption layer of the persistence adapter: JPA concerns never leak into the domain.
 * </p>
 */
public final class ShipmentOrderPersistenceAssembler {

    private ShipmentOrderPersistenceAssembler() {
    }

    /**
     * Reconstitutes a domain aggregate, including its lines, from its persistence entity.
     *
     * @param entity persistence entity, may be {@code null}
     * @return the reconstituted aggregate, or {@code null} when the entity is {@code null}
     */
    public static ShipmentOrder toDomainFromPersistence(ShipmentOrderPersistenceEntity entity) {
        if (entity == null) return null;
        var lines = new ArrayList<ShipmentOrderLine>();
        if (entity.getLines() != null) {
            for (var item : entity.getLines()) {
                lines.add(ShipmentOrderLine.reconstitute(
                        item.getId(), item.getProductId(), item.getQuantity(), item.getLotCode(), item.getExpiresOn()));
            }
        }
        return ShipmentOrder.reconstitute(
                entity.getId(),
                entity.getSupplierId(),
                entity.getMinimarketId(),
                lines,
                entity.getStatus(),
                entity.getDecidedBy(),
                entity.getDecidedAt(),
                entity.getRejectionReason(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    /**
     * Maps a domain aggregate, including its lines, to a persistence entity.
     *
     * @param shipmentOrder domain aggregate, may be {@code null}
     * @return the persistence entity, or {@code null} when the aggregate is {@code null}
     */
    public static ShipmentOrderPersistenceEntity toPersistenceFromDomain(ShipmentOrder shipmentOrder) {
        if (shipmentOrder == null) return null;
        var entity = new ShipmentOrderPersistenceEntity();
        if (shipmentOrder.getId() != null) {
            entity.setId(shipmentOrder.getId());
        }
        entity.setSupplierId(shipmentOrder.getSupplierId());
        entity.setMinimarketId(shipmentOrder.getMinimarketId());
        entity.setStatus(shipmentOrder.getStatus());
        entity.setDecidedBy(shipmentOrder.getDecidedBy());
        entity.setDecidedAt(shipmentOrder.getDecidedAt());
        entity.setRejectionReason(shipmentOrder.getRejectionReason());
        entity.setCreatedAt(shipmentOrder.getCreatedAt());
        var lines = new ArrayList<ShipmentOrderLinePersistenceEntity>();
        for (var line : shipmentOrder.getLines()) {
            var persistenceLine = new ShipmentOrderLinePersistenceEntity();
            if (line.getId() != null) {
                persistenceLine.setId(line.getId());
            }
            persistenceLine.setShipmentOrder(entity);
            persistenceLine.setProductId(line.getProductId());
            persistenceLine.setQuantity(line.getQuantity());
            persistenceLine.setLotCode(line.getLotCode());
            persistenceLine.setExpiresOn(line.getExpiresOn());
            lines.add(persistenceLine);
        }
        entity.setLines(lines);
        return entity;
    }
}
