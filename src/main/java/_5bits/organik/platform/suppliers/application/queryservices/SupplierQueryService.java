package _5bits.organik.platform.suppliers.application.queryservices;

import _5bits.organik.platform.suppliers.domain.model.aggregates.Supplier;
import _5bits.organik.platform.suppliers.domain.model.queries.GetAllSuppliersQuery;
import _5bits.organik.platform.suppliers.domain.model.queries.GetSupplierByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for Supplier query operations.
 */
public interface SupplierQueryService {

    /**
     * Handles the {@link GetSupplierByIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    Optional<Supplier> handle(GetSupplierByIdQuery query);

    /**
     * Handles the {@link GetAllSuppliersQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    List<Supplier> handle(GetAllSuppliersQuery query);
}
