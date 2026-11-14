package _5bits.organik.platform.inventory.application.queryservices;

import _5bits.organik.platform.inventory.domain.model.aggregates.InventoryMovement;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllInventoryMovementsByStockLotIdQuery;
import _5bits.organik.platform.inventory.domain.model.queries.GetInventoryMovementByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for InventoryMovement query operations.
 */
public interface InventoryMovementQueryService {

    /**
     * Handles the {@link GetInventoryMovementByIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    Optional<InventoryMovement> handle(GetInventoryMovementByIdQuery query);

    /**
     * Handles the {@link GetAllInventoryMovementsByStockLotIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    List<InventoryMovement> handle(GetAllInventoryMovementsByStockLotIdQuery query);
}
