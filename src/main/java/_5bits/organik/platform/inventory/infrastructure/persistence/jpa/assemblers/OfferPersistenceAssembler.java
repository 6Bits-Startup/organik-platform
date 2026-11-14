package _5bits.organik.platform.inventory.infrastructure.persistence.jpa.assemblers;

import _5bits.organik.platform.inventory.domain.model.aggregates.Offer;
import _5bits.organik.platform.inventory.infrastructure.persistence.jpa.entities.OfferPersistenceEntity;


/**
 * Assembler that maps between the {@link Offer} domain aggregate and its JPA persistence entity.
 * <p>
 * Acts as the anti-corruption layer of the persistence adapter: JPA concerns never leak into the domain.
 * </p>
 */
public final class OfferPersistenceAssembler {

    private OfferPersistenceAssembler() {
    }

    /**
     * Reconstitutes a domain aggregate from its persistence entity.
     *
     * @param entity persistence entity, may be {@code null}
     * @return the reconstituted aggregate, or {@code null} when the entity is {@code null}
     */
    public static Offer toDomainFromPersistence(OfferPersistenceEntity entity) {
        if (entity == null) return null;
        return Offer.reconstitute(
                entity.getId(),
                entity.getStockLotId(),
                entity.getPrice(),
                entity.getStartsOn(),
                entity.getEndsOn());
    }

    /**
     * Maps a domain aggregate to a persistence entity.
     *
     * @param offer domain aggregate, may be {@code null}
     * @return the persistence entity, or {@code null} when the aggregate is {@code null}
     */
    public static OfferPersistenceEntity toPersistenceFromDomain(Offer offer) {
        if (offer == null) return null;
        var entity = new OfferPersistenceEntity();
        if (offer.getId() != null) {
            entity.setId(offer.getId());
        }
        entity.setStockLotId(offer.getStockLotId());
        entity.setPrice(offer.getPrice());
        entity.setStartsOn(offer.getStartsOn());
        entity.setEndsOn(offer.getEndsOn());
        return entity;
    }
}
