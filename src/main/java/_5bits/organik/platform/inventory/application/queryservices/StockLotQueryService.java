package _5bits.organik.platform.inventory.application.queryservices;

import _5bits.organik.platform.inventory.domain.model.aggregates.StockLot;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllStockLotsByMinimarketIdQuery;
import _5bits.organik.platform.inventory.domain.model.queries.GetStockLotByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for StockLot query operations.
 */
public interface StockLotQueryService {

    /**
     * Handles the {@link GetStockLotByIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    Optional<StockLot> handle(GetStockLotByIdQuery query);

    /**
     * Handles the {@link GetAllStockLotsByMinimarketIdQuery} query.
     *
     * @param query query object
     * @return the query result
     */
    List<StockLot> handle(GetAllStockLotsByMinimarketIdQuery query);
}
