package _5bits.organik.platform.products.application.queryservices;

import _5bits.organik.platform.products.domain.model.aggregates.Category;
import _5bits.organik.platform.products.domain.model.queries.GetAllCategoriesQuery;
import _5bits.organik.platform.products.domain.model.queries.GetCategoryByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for Category query operations.
 */
public interface CategoryQueryService {

    /**
     * Handles the {@link GetCategoryByIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    Optional<Category> handle(GetCategoryByIdQuery query);

    /**
     * Handles the {@link GetAllCategoriesQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    List<Category> handle(GetAllCategoriesQuery query);
}
