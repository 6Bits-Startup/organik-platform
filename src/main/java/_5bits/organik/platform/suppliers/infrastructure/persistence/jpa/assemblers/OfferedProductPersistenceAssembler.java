package _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.assemblers;

import _5bits.organik.platform.suppliers.domain.model.aggregates.OfferedProduct;
import _5bits.organik.platform.suppliers.infrastructure.persistence.jpa.entities.OfferedProductPersistenceEntity;


/**
 * Assembler that maps between the {@link OfferedProduct} domain aggregate and its JPA persistence entity.
 * <p>
 * Acts as the anti-corruption layer of the persistence adapter: JPA concerns never leak into the domain.
 * </p>
 */
public final class OfferedProductPersistenceAssembler {

    private OfferedProductPersistenceAssembler() {
    }

    /**
     * Reconstitutes a domain aggregate from its persistence entity.
     *
     * @param entity persistence entity, may be {@code null}
     * @return the reconstituted aggregate, or {@code null} when the entity is {@code null}
     */
    public static OfferedProduct toDomainFromPersistence(OfferedProductPersistenceEntity entity) {
        if (entity == null) return null;
        return OfferedProduct.reconstitute(
                entity.getId(),
                entity.getSupplierId(),
                entity.getProductId(),
                entity.getLotCode(),
                entity.getAvailableQuantity(),
                entity.getExpiresOn());
    }

    /**
     * Maps a domain aggregate to a persistence entity.
     *
     * @param offeredProduct domain aggregate, may be {@code null}
     * @return the persistence entity, or {@code null} when the aggregate is {@code null}
     */
    public static OfferedProductPersistenceEntity toPersistenceFromDomain(OfferedProduct offeredProduct) {
        if (offeredProduct == null) return null;
        var entity = new OfferedProductPersistenceEntity();
        if (offeredProduct.getId() != null) {
            entity.setId(offeredProduct.getId());
        }
        entity.setSupplierId(offeredProduct.getSupplierId());
        entity.setProductId(offeredProduct.getProductId());
        entity.setLotCode(offeredProduct.getLotCode());
        entity.setAvailableQuantity(offeredProduct.getAvailableQuantity());
        entity.setExpiresOn(offeredProduct.getExpiresOn());
        return entity;
    }
}
