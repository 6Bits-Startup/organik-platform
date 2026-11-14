package _5bits.organik.platform.inventory.domain.repositories;

import _5bits.organik.platform.inventory.domain.model.aggregates.InventoryMovement;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link InventoryMovement} aggregate.
 * <p>
 * Pure Java interface with no framework dependencies. The infrastructure layer provides the
 * adapter that implements it with Spring Data JPA.
 * </p>
 */
public interface InventoryMovementRepository {

    Optional<InventoryMovement> findById(Long id);

    List<InventoryMovement> findAll();

    List<InventoryMovement> findAllByStockLotId(StockLotId stockLotId);

    InventoryMovement save(InventoryMovement inventoryMovement);

    boolean existsById(Long id);
}
