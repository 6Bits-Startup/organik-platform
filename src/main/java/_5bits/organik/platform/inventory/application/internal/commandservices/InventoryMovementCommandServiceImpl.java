package _5bits.organik.platform.inventory.application.internal.commandservices;

import _5bits.organik.platform.inventory.application.commandservices.InventoryMovementCommandService;
import _5bits.organik.platform.inventory.domain.model.aggregates.InventoryMovement;
import _5bits.organik.platform.inventory.domain.model.commands.RegisterInventoryMovementCommand;
import _5bits.organik.platform.inventory.domain.repositories.InventoryMovementRepository;
import _5bits.organik.platform.inventory.domain.repositories.StockLotRepository;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that handles InventoryMovement commands.
 */
@Service
public class InventoryMovementCommandServiceImpl implements InventoryMovementCommandService {
    private final InventoryMovementRepository inventoryMovementRepository;
    private final StockLotRepository stockLotRepository;

    public InventoryMovementCommandServiceImpl(InventoryMovementRepository inventoryMovementRepository, StockLotRepository stockLotRepository) {
        this.inventoryMovementRepository = inventoryMovementRepository;
        this.stockLotRepository = stockLotRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(RegisterInventoryMovementCommand command) {
        var result = stockLotRepository.findById(command.stockLotId().value());
        if (result.isEmpty())
            return Result.failure(ApplicationError.notFound("StockLot", command.stockLotId().value().toString()));
        var stockLot = result.get();
        if (!stockLot.canApplyMovement(command.type(), command.quantity()))
            return Result.failure(ApplicationError.businessRuleViolation("insufficient-stock",
                    "The stock lot only has %d units".formatted(stockLot.getQuantity().value())));
        try {
            stockLot.applyMovement(command.type(), command.quantity());
            stockLotRepository.save(stockLot);
            var movement = inventoryMovementRepository.save(new InventoryMovement(command));
            return Result.success(movement.getId());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("register-inventory-movement", e.getMessage()));
        }
    }
}
