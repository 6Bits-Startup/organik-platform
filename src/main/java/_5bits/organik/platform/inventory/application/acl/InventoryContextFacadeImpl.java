package _5bits.organik.platform.inventory.application.acl;

import _5bits.organik.platform.inventory.application.queryservices.InventoryMovementQueryService;
import _5bits.organik.platform.inventory.application.queryservices.StockLotQueryService;
import _5bits.organik.platform.inventory.domain.model.aggregates.StockLot;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllInventoryMovementsByStockLotIdQuery;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllStockLotsByMinimarketIdQuery;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MinimarketId;
import _5bits.organik.platform.inventory.domain.model.valueobjects.MovementType;
import _5bits.organik.platform.inventory.domain.model.valueobjects.StockLotId;
import _5bits.organik.platform.inventory.interfaces.acl.InventoryContextFacade;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of the {@link InventoryContextFacade} ACL facade based on the query services of the bounded context.
 */
@Service
public class InventoryContextFacadeImpl implements InventoryContextFacade {
    private final StockLotQueryService stockLotQueryService;
    private final InventoryMovementQueryService inventoryMovementQueryService;

    public InventoryContextFacadeImpl(StockLotQueryService stockLotQueryService, InventoryMovementQueryService inventoryMovementQueryService) {
        this.stockLotQueryService = stockLotQueryService;
        this.inventoryMovementQueryService = inventoryMovementQueryService;
    }

    @Override
    public long countStockLotsByMinimarketId(String minimarketId) {
        return stockLotsOf(minimarketId).size();
    }

    @Override
    public long countLowStockLotsByMinimarketId(String minimarketId) {
        return stockLotsOf(minimarketId).stream().filter(StockLot::isLowStock).count();
    }

    @Override
    public long countStockLotsExpiringWithinDays(String minimarketId, int days) {
        var today = LocalDate.now();
        var limit = today.plusDays(days);
        return stockLotsOf(minimarketId).stream()
                .map(stockLot -> stockLot.getExpirationDate().value())
                .filter(expirationDate -> !expirationDate.isBefore(today) && !expirationDate.isAfter(limit))
                .count();
    }

    @Override
    public long countExpiredStockLotsByMinimarketId(String minimarketId) {
        var today = LocalDate.now();
        return stockLotsOf(minimarketId).stream()
                .filter(stockLot -> stockLot.getExpirationDate().value().isBefore(today))
                .count();
    }

    @Override
    public long sumWasteUnitsByMinimarketId(String minimarketId) {
        return stockLotsOf(minimarketId).stream()
                .flatMap(stockLot -> inventoryMovementQueryService.handle(
                        new GetAllInventoryMovementsByStockLotIdQuery(new StockLotId(stockLot.getId()))).stream())
                .filter(movement -> movement.getType() == MovementType.WASTE)
                .mapToLong(movement -> movement.getQuantity().value())
                .sum();
    }

    private List<StockLot> stockLotsOf(String minimarketId) {
        return stockLotQueryService.handle(new GetAllStockLotsByMinimarketIdQuery(new MinimarketId(minimarketId)));
    }
}
