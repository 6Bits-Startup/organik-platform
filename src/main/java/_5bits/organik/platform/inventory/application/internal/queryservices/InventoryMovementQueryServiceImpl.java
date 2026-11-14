package _5bits.organik.platform.inventory.application.internal.queryservices;

import _5bits.organik.platform.inventory.application.queryservices.InventoryMovementQueryService;
import _5bits.organik.platform.inventory.domain.model.aggregates.InventoryMovement;
import _5bits.organik.platform.inventory.domain.model.queries.GetAllInventoryMovementsByStockLotIdQuery;
import _5bits.organik.platform.inventory.domain.model.queries.GetInventoryMovementByIdQuery;
import _5bits.organik.platform.inventory.domain.repositories.InventoryMovementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that handles InventoryMovement queries.
 */
@Service
public class InventoryMovementQueryServiceImpl implements InventoryMovementQueryService {
    private final InventoryMovementRepository inventoryMovementRepository;

    public InventoryMovementQueryServiceImpl(InventoryMovementRepository inventoryMovementRepository) {
        this.inventoryMovementRepository = inventoryMovementRepository;
    }

    @Override
    public Optional<InventoryMovement> handle(GetInventoryMovementByIdQuery query) {
        return inventoryMovementRepository.findById(query.id());
    }

    @Override
    public List<InventoryMovement> handle(GetAllInventoryMovementsByStockLotIdQuery query) {
        return inventoryMovementRepository.findAllByStockLotId(query.stockLotId());
    }
}
