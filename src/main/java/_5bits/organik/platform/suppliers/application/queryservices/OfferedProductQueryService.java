package _5bits.organik.platform.suppliers.application.queryservices;

import _5bits.organik.platform.suppliers.domain.model.aggregates.OfferedProduct;
import _5bits.organik.platform.suppliers.domain.model.queries.GetAllOfferedProductsBySupplierIdQuery;
import _5bits.organik.platform.suppliers.domain.model.queries.GetOfferedProductByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for OfferedProduct query operations.
 */
public interface OfferedProductQueryService {

    /**
     * Handles the {@link GetOfferedProductByIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    Optional<OfferedProduct> handle(GetOfferedProductByIdQuery query);

    /**
     * Handles the {@link GetAllOfferedProductsBySupplierIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    List<OfferedProduct> handle(GetAllOfferedProductsBySupplierIdQuery query);
}
