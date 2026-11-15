package _5bits.organik.platform.requisition.infrastructure.persistence.jpa.assemblers;

import _5bits.organik.platform.requisition.domain.model.aggregates.SupplyRequest;
import _5bits.organik.platform.requisition.infrastructure.persistence.jpa.entities.SupplyRequestPersistenceEntity;


/**
 * Assembler that maps between the {@link SupplyRequest} domain aggregate and its JPA persistence entity.
 * <p>
 * Acts as the anti-corruption layer of the persistence adapter: JPA concerns never leak into the domain.
 * </p>
 */
public final class SupplyRequestPersistenceAssembler {

    private SupplyRequestPersistenceAssembler() {
    }

    /**
     * Reconstitutes a domain aggregate from its persistence entity.
     *
     * @param entity persistence entity, may be {@code null}
     * @return the reconstituted aggregate, or {@code null} when the entity is {@code null}
     */
    public static SupplyRequest toDomainFromPersistence(SupplyRequestPersistenceEntity entity) {
        if (entity == null) return null;
        return SupplyRequest.reconstitute(
                entity.getId(),
                entity.getProductId(),
                entity.getSupplierId(),
                entity.getMinimarketId(),
                entity.getQuantity(),
                entity.getReason(),
                entity.getRequiredDate(),
                entity.getPriority(),
                entity.getStatus());
    }

    /**
     * Maps a domain aggregate to a persistence entity.
     *
     * @param supplyRequest domain aggregate, may be {@code null}
     * @return the persistence entity, or {@code null} when the aggregate is {@code null}
     */
    public static SupplyRequestPersistenceEntity toPersistenceFromDomain(SupplyRequest supplyRequest) {
        if (supplyRequest == null) return null;
        var entity = new SupplyRequestPersistenceEntity();
        if (supplyRequest.getId() != null) {
            entity.setId(supplyRequest.getId());
        }
        entity.setProductId(supplyRequest.getProductId());
        entity.setSupplierId(supplyRequest.getSupplierId());
        entity.setMinimarketId(supplyRequest.getMinimarketId());
        entity.setQuantity(supplyRequest.getQuantity());
        entity.setReason(supplyRequest.getReason());
        entity.setRequiredDate(supplyRequest.getRequiredDate());
        entity.setPriority(supplyRequest.getPriority());
        entity.setStatus(supplyRequest.getStatus());
        return entity;
    }
}
