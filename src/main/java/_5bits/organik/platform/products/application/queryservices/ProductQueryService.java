package _5bits.organik.platform.products.application.queryservices;

import _5bits.organik.platform.products.domain.model.aggregates.Product;
import _5bits.organik.platform.products.domain.model.queries.GetAllProductsByCategoryIdQuery;
import _5bits.organik.platform.products.domain.model.queries.GetAllProductsQuery;
import _5bits.organik.platform.products.domain.model.queries.GetProductByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for Product query operations.
 */
public interface ProductQueryService {

    /**
     * Handles the {@link GetProductByIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    Optional<Product> handle(GetProductByIdQuery query);

    /**
     * Handles the {@link GetAllProductsQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    List<Product> handle(GetAllProductsQuery query);

    /**
     * Handles the {@link GetAllProductsByCategoryIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    List<Product> handle(GetAllProductsByCategoryIdQuery query);
}
