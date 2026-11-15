package _5bits.organik.platform.inventory.application.internal.queryservices;

import _5bits.organik.platform.inventory.application.queryservices.StockLotQueryService;
import _5bits.organik.platform.inventory.domain.model.aggregates.StockLot;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllStockLotsByMinimarketIdQuery;
import _5bits.organik.platform.inventory.domain.model.queries.GetStockLotByIdQuery;
import _5bits.organik.platform.inventory.domain.repositories.StockLotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that handles StockLot queries.
 */
@Service
public class StockLotQueryServiceImpl implements StockLotQueryService {
    private final StockLotRepository stockLotRepository;

    public StockLotQueryServiceImpl(StockLotRepository stockLotRepository) {
        this.stockLotRepository = stockLotRepository;
    }

    @Override
    public Optional<StockLot> handle(GetStockLotByIdQuery query) {
        return stockLotRepository.findById(query.id());
    }

    @Override
    public List<StockLot> handle(GetAllStockLotsByMinimarketIdQuery query) {
        return stockLotRepository.findAllByMinimarketId(query.minimarketId());
    }
}
