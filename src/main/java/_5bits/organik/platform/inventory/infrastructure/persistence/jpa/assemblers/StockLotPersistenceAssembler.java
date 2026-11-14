package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.assemblers;

import _5bits.organik.platform.inventory.domain.model.aggregates.StockLot;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.entities.StockLotPersistenceEntity;


/**
 * Assembler that maps between the {@link StockLot} domain aggregate and its JPA persistence entity.
 * <p>
 * Acts as the anti-corruption layer of the persistence adapter: JPA concerns never leak into the domain.
 * </p>
 */
public final class StockLotPersistenceAssembler {

    private StockLotPersistenceAssembler() {
    }

    /**
     * Reconstitutes a domain aggregate from its persistence entity.
     *
     * @param entity persistence entity, may be {@code null}
     * @return the reconstituted aggregate, or {@code null} when the entity is {@code null}
     */
    public static StockLot toDomainFromPersistence(StockLotPersistenceEntity entity) {
        if (entity == null) return null;
        return StockLot.reconstitute(
                entity.getId(),
                entity.getMinimarketId(),
                entity.getProductId(),
                entity.getLotCode(),
                entity.getQuantity(),
                entity.getMinimumStock(),
                entity.getExpirationDate());
    }

    /**
     * Maps a domain aggregate to a persistence entity.
     *
     * @param stockLot domain aggregate, may be {@code null}
     * @return the persistence entity, or {@code null} when the aggregate is {@code null}
     */
    public static StockLotPersistenceEntity toPersistenceFromDomain(StockLot stockLot) {
        if (stockLot == null) return null;
        var entity = new StockLotPersistenceEntity();
        if (stockLot.getId() != null) {
            entity.setId(stockLot.getId());
        }
        entity.setMinimarketId(stockLot.getMinimarketId());
        entity.setProductId(stockLot.getProductId());
        entity.setLotCode(stockLot.getLotCode());
        entity.setQuantity(stockLot.getQuantity());
        entity.setMinimumStock(stockLot.getMinimumStock());
        entity.setExpirationDate(stockLot.getExpirationDate());
        return entity;
    }
}
