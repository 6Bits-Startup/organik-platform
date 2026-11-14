package _5bits.organik.platform.inventory.application.queryservices;

import _5bits.organik.platform.inventory.domain.model.aggregates.Offer;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllOffersByStockLotIdQuery;
import _5bits.organik.platform.inventory.domain.model.queries.GetOfferByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for Offer query operations.
 */
public interface OfferQueryService {

    /**
     * Handles the {@link GetOfferByIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    Optional<Offer> handle(GetOfferByIdQuery query);

    /**
     * Handles the {@link GetAllOffersByStockLotIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    List<Offer> handle(GetAllOffersByStockLotIdQuery query);
}
