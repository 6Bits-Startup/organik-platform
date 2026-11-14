package _5bits.organik.platform.inventory.application.internal.commandservices;

import _5bits.organik.platform.inventory.application.commandservices.StockLotCommandService;
import _5bits.organik.platform.inventory.domain.model.aggregates.StockLot;
import _5bits.organik.platform.inventory.domain.model.commands.CreateStockLotCommand;
import _5bits.organik.platform.inventory.domain.repositories.StockLotRepository;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that handles StockLot commands.
 */
@Service
public class StockLotCommandServiceImpl implements StockLotCommandService {
    private final StockLotRepository stockLotRepository;

    public StockLotCommandServiceImpl(StockLotRepository stockLotRepository) {
        this.stockLotRepository = stockLotRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateStockLotCommand command) {
        if (stockLotRepository.existsByMinimarketIdAndLotCode(command.minimarketId(), command.lotCode()))
            return Result.failure(ApplicationError.conflict("StockLot",
                    "Lot code '%s' already exists in minimarket '%s'".formatted(command.lotCode().value(), command.minimarketId().value())));
        var stockLot = new StockLot(command);
        try {
            stockLot = stockLotRepository.save(stockLot);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-stock-lot", e.getMessage()));
        }
        return Result.success(stockLot.getId());
    }
}
