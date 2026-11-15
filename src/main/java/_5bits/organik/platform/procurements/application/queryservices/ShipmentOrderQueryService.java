package _5bits.organik.platform.procurements.application.queryservices;

import _5bits.organik.platform.procurements.domain.model.aggregates.ShipmentOrder;
import _5bits.organik.platform.procurements.domain.model.queries.GetShipmentOrderByIdQuery;
import _5bits.organik.platform.procurements.domain.model.queries.GetShipmentOrdersByMinimarketIdQuery;
import _5bits.organik.platform.procurements.domain.model.queries.GetShipmentOrdersBySupplierIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for ShipmentOrder query operations.
 */
public interface ShipmentOrderQueryService {

    /**
     * Handles the {@link GetShipmentOrderByIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    Optional<ShipmentOrder> handle(GetShipmentOrderByIdQuery query);

    /**
     * Handles the {@link GetShipmentOrdersBySupplierIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    List<ShipmentOrder> handle(GetShipmentOrdersBySupplierIdQuery query);

    /**
     * Handles the {@link GetShipmentOrdersByMinimarketIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    List<ShipmentOrder> handle(GetShipmentOrdersByMinimarketIdQuery query);
}
