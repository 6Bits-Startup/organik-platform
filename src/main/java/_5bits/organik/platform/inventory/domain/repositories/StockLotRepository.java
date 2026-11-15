package _5bits.organik.platform.inventory.domain.repositories;

import _5bits.organik.platform.inventory.domain.model.aggregates.StockLot;
import _5bits.organik.platform.inventory.domain.model.valueobjects.LotCode;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MinimarketId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link StockLot} aggregate.
 * <p>
 * Pure Java interface with no framework dependencies. The infrastructure layer provides the
 * adapter that implements it with Spring Data JPA.
 * </p>
 */
public interface StockLotRepository {

    Optional<StockLot> findById(Long id);

    List<StockLot> findAll();

    List<StockLot> findAllByMinimarketId(MinimarketId minimarketId);

    boolean existsByMinimarketIdAndLotCode(MinimarketId minimarketId, LotCode lotCode);

    StockLot save(StockLot stockLot);

    boolean existsById(Long id);
}
