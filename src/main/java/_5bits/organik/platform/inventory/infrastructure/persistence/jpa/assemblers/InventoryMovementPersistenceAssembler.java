package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.assemblers;

import _5bits.organik.platform.inventory.domain.model.aggregates.InventoryMovement;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.entities.InventoryMovementPersistenceEntity;


/**
 * Assembler that maps between the {@link InventoryMovement} domain aggregate and its JPA persistence entity.
 * <p>
 * Acts as the anti-corruption layer of the persistence adapter: JPA concerns never leak into the domain.
 * </p>
 */
public final class InventoryMovementPersistenceAssembler {

    private InventoryMovementPersistenceAssembler() {
    }

    /**
     * Reconstitutes a domain aggregate from its persistence entity.
     *
     * @param entity persistence entity, may be {@code null}
     * @return the reconstituted aggregate, or {@code null} when the entity is {@code null}
     */
    public static InventoryMovement toDomainFromPersistence(InventoryMovementPersistenceEntity entity) {
        if (entity == null) return null;
        return InventoryMovement.reconstitute(
                entity.getId(),
                entity.getStockLotId(),
                entity.getType(),
                entity.getQuantity(),
                entity.getReason(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    /**
     * Maps a domain aggregate to a persistence entity.
     *
     * @param inventoryMovement domain aggregate, may be {@code null}
     * @return the persistence entity, or {@code null} when the aggregate is {@code null}
     */
    public static InventoryMovementPersistenceEntity toPersistenceFromDomain(InventoryMovement inventoryMovement) {
        if (inventoryMovement == null) return null;
        var entity = new InventoryMovementPersistenceEntity();
        if (inventoryMovement.getId() != null) {
            entity.setId(inventoryMovement.getId());
        }
        entity.setStockLotId(inventoryMovement.getStockLotId());
        entity.setType(inventoryMovement.getType());
        entity.setQuantity(inventoryMovement.getQuantity());
        entity.setReason(inventoryMovement.getReason());
        entity.setCreatedAt(inventoryMovement.getCreatedAt());
        return entity;
    }
}
