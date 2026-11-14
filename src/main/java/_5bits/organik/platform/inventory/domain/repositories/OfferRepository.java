package _5bits.organik.platform.inventory.domain.repositories;

import _5bits.organik.platform.inventory.domain.model.aggregates.Offer;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link Offer} aggregate.
 * <p>
 * Pure Java interface with no framework dependencies. The infrastructure layer provides the
 * adapter that implements it with Spring Data JPA.
 * </p>
 */
public interface OfferRepository {

    Optional<Offer> findById(Long id);

    List<Offer> findAll();

    List<Offer> findAllByStockLotId(StockLotId stockLotId);

    Offer save(Offer offer);

    boolean existsById(Long id);
}
