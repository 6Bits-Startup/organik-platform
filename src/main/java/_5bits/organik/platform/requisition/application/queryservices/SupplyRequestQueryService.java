package _5bits.organik.platform.requisition.application.queryservices;

import _5bits.organik.platform.requisition.domain.model.aggregates.SupplyRequest;
import _5bits.organik.platform.requisition.domain.model.queries.GetAllSupplyRequestsByMinimarketIdQuery;
import _5bits.organik.platform.requisition.domain.model.queries.GetAllSupplyRequestsBySupplierIdQuery;
import _5bits.organik.platform.requisition.domain.model.queries.GetSupplyRequestByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for SupplyRequest query operations.
 */
public interface SupplyRequestQueryService {

    /**
     * Handles the {@link GetSupplyRequestByIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    Optional<SupplyRequest> handle(GetSupplyRequestByIdQuery query);

    /**
     * Handles the {@link GetAllSupplyRequestsByMinimarketIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    List<SupplyRequest> handle(GetAllSupplyRequestsByMinimarketIdQuery query);

    /**
     * Handles the {@link GetAllSupplyRequestsBySupplierIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    List<SupplyRequest> handle(GetAllSupplyRequestsBySupplierIdQuery query);
}
