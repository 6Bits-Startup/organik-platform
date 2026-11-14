package _5bits.organik.platform.requisition.domain.repositories;

import _5bits.organik.platform.requisition.domain.model.aggregates.SupplyRequest;
import _5bits.organik.platform.requisition.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.requisition.domain.model.valueobjects.SupplierId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link SupplyRequest} aggregate.
 * <p>
 * Pure Java interface with no framework dependencies. The infrastructure layer provides the
 * adapter that implements it with Spring Data JPA.
 * </p>
 */
public interface SupplyRequestRepository {

    Optional<SupplyRequest> findById(Long id);

    List<SupplyRequest> findAll();

    List<SupplyRequest> findAllByMinimarketId(MinimarketId minimarketId);

    List<SupplyRequest> findAllBySupplierId(SupplierId supplierId);

    SupplyRequest save(SupplyRequest supplyRequest);

    boolean existsById(Long id);
}
