package _5bits.organik.platform.procurements.domain.repositories;

import _5bits.organik.platform.procurements.domain.model.aggregates.ShipmentOrder;
import _5bits.organik.platform.procurements.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.procurements.domain.model.valueobjects.SupplierId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link ShipmentOrder} aggregate.
 * <p>
 * Pure Java interface with no framework dependencies. The infrastructure layer provides the
 * adapter that implements it with Spring Data JPA.
 * </p>
 */
public interface ShipmentOrderRepository {

    Optional<ShipmentOrder> findById(Long id);

    List<ShipmentOrder> findAll();

    List<ShipmentOrder> findAllBySupplierId(SupplierId supplierId);

    List<ShipmentOrder> findAllByMinimarketId(MinimarketId minimarketId);

    ShipmentOrder save(ShipmentOrder shipmentOrder);

    boolean existsById(Long id);
}
